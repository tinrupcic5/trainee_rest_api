package com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.level.common.interfaces.toEntity
import com.bitroot.trainee.restapi.domain.role.adapter.outgoing.jpa.RoleEntityJpaRepository
import com.bitroot.trainee.restapi.domain.role.common.interfaces.Roles
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.SchoolDetailsEntityJpaRepository
import com.bitroot.trainee.restapi.domain.settings.adapter.outgoing.jpa.SettingsRepository
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Language
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Settings
import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.common.interfaces.domainToEntity
import com.bitroot.trainee.restapi.domain.user.common.interfaces.optionalDomainToEntity
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsEntity
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsEntityJpaRepository
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.entityToDomain
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.email.EmailService
import com.bitroot.trainee.restapi.email.LanguageEnum
import com.bitroot.trainee.restapi.errorhandling.LoginFailedException
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.EmailSentResult
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class UserRepositoryImpl(
    private val userEntityJpaRepository: UserEntityJpaRepository,
    private val roleEntityJpaRepository: RoleEntityJpaRepository,
    private val userDetailsEntityJpaRepository: UserDetailsEntityJpaRepository,
    private val schoolDetailsEntityJpaRepository: SchoolDetailsEntityJpaRepository,
    private val settingsRepository: SettingsRepository,
    private val emailService: EmailService,
) : UserRepository {
    private val logger = KotlinLogging.logger { }

    @Modifying
    override fun register(user: User, userDetails: UserDetails, language: String): String {
        val roleEntity = roleEntityJpaRepository.findRoleByName(user.role.name.value) ?: return "Role not found."
        userEntityJpaRepository.save(
            UserEntity(
                id = user.id?.value,
                userName = user.userName.value,
                password = user.password.value,
                createdAt = user.createdAt,
                lastTimeOnline = user.lastTimeOnline,
                role = roleEntity,
                isEnabled = user.isEnabled.value,
            ),
        ).toDomain()

        val userResult = login(user.userName.value, user.password.value)
        val schoolDetailsEntityResult =
            schoolDetailsEntityJpaRepository.getSchoolDetailsById(userDetails.schoolDetails.id?.value!!)

        val userDetailsEntity = userDetailsEntityJpaRepository.save(
            UserDetailsEntity(
                id = userDetails.id?.value,
                user = userResult.optionalDomainToEntity(),
                name = userDetails.name.value.replaceFirstChar { it.uppercase() },
                lastName = userDetails.lastName.value.replaceFirstChar { it.uppercase() },
                email = userDetails.email.value,
                phoneNumber = userDetails.phoneNumber?.value,
                schoolDetailsEntity = schoolDetailsEntityResult,
                trainingLevelEntity = userDetails.trainingLevel?.toEntity(),
            ),
        )

        val settings = settingsRepository.saveSettings(
            Settings(
                userDetails = userDetailsEntity.entityToDomain(),
                language = Language(LanguageEnum.valueOf(language).name),
                isRegistrationEmailSent = false,
            ),
        )

        logger.info { "User saved: $userResult." }

        return when (
            emailService.sendSuccessfullRegistrationEmailMessage(
                to = userDetails.email.value!!,
                userDetails = userDetailsEntity.entityToDomain(),
                schoolName = schoolDetailsEntityResult.schoolEntity.schoolName,
                languageSettings = Language(LanguageEnum.valueOf(language).name),
            )
        ) {
            is EmailSentResult.Failed ->
                "User ${userDetailsEntity.name} ${userDetailsEntity.lastName} saved but mail not sent."

            is EmailSentResult.Success -> {
                settingsRepository.saveSettings(settings.copy(isRegistrationEmailSent = true))
                "User ${userDetailsEntity.name} ${userDetailsEntity.lastName} has been saved."
            }
        }
    }

    override fun saveLastTimeOnline(user: User): String {
        userEntityJpaRepository.save(user.domainToEntity())
        return "User last time login saved."
    }

    override fun saveGuest(user: User, language: String): UserDetails {
        val roleEntity = roleEntityJpaRepository.findRoleByName(Roles.VIEWER.toString())

        userEntityJpaRepository.save(
            user.domainToEntity().copy(role = roleEntity!!),
        ).toDomain()

        val userResult = login(user.userName.value, user.password.value)
        val schoolDetailsEntityResult = schoolDetailsEntityJpaRepository.getSchoolDetailsById(1)
        logger.info { "User Guest saved: $userResult." }
        val userDetails = userDetailsEntityJpaRepository.save(
            UserDetailsEntity(
                id = null,
                user = userResult.optionalDomainToEntity(),
                name = user.userName.value,
                lastName = user.password.value,
                email = null,
                phoneNumber = null,
                schoolDetailsEntity = schoolDetailsEntityResult,
                trainingLevelEntity = null,
            ),
        ).entityToDomain()
        settingsRepository.saveSettings(
            Settings(
                userDetails = userDetails,
                language = Language(LanguageEnum.valueOf(language).name),
                isRegistrationEmailSent = false,
            ),
        )

        return userDetails
    }

    override fun updateUserPassword(userId: Long, newPassword: String): Boolean {
        userEntityJpaRepository.updateUserPassword(userId, newPassword)
        return true
    }

    override fun login(username: String, password: String): Optional<User> =
        userEntityJpaRepository.login(username, password).optionalToOptionalDomain()

    override fun getUserByUsername(username: String): Optional<UserDetails> {
        val userOptional = userEntityJpaRepository.getUserByUsername(username).optionalToOptionalDomain()
        if (userOptional.isEmpty) {
            throw LoginFailedException("Login failed. Invalid username.")
        }
        val user = userOptional.get()
        return Optional.of(getUserDetails(user.id?.value!!))
    }

    override fun getUserById(userId: Long): User =
        userEntityJpaRepository.getReferenceById(userId).toDomain()

    override fun deleteUser(userId: Long): String {
        val usr = userEntityJpaRepository.getReferenceById(userId)
        val userDetails = userDetailsEntityJpaRepository.getUserDetailsByUserId(userId)
        userEntityJpaRepository.delete(usr)
        return "User ${userDetails.name} ${userDetails.lastName} has been deleted."
    }

    @Modifying
    override fun setUserEnableStatus(copy: User): String {
        userEntityJpaRepository.save(copy.domainToEntity())
        return "User by username: ${copy.userName} is enabled: ${copy.isEnabled} ."
    }

    override fun getAllUsers(): List<User>? =
        userEntityJpaRepository.getAllUsers()?.map { it.toDomain() }

    fun getUserDetails(userId: Long): UserDetails =
        userDetailsEntityJpaRepository.getUserDetailsByUserId(userId).entityToDomain()
}
