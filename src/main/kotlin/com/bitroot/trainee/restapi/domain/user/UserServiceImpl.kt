package com.bitroot.trainee.restapi.domain.user

import com.bitroot.trainee.restapi.domain.qrcode.QrCodeService
import com.bitroot.trainee.restapi.domain.qrcode.properties.QrCodeGeneratorService
import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleId
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleName
import com.bitroot.trainee.restapi.domain.settings.SettingsService
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Language
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.user.adapter.incoming.web.UserChangePasswordRequest
import com.bitroot.trainee.restapi.domain.user.adapter.incoming.web.UserRegisterRequest
import com.bitroot.trainee.restapi.domain.user.adapter.incoming.web.requestToDomain
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.UserRepository
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web.UserDto
import com.bitroot.trainee.restapi.domain.user.common.interfaces.Password
import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserEnabled
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserName
import com.bitroot.trainee.restapi.domain.user.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web.requestToDomain
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsRepository
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.web.UserDetailsDto
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.optionalDomainToOptionalDto
import com.bitroot.trainee.restapi.email.EmailService
import com.bitroot.trainee.restapi.email.LanguageEnum
import com.bitroot.trainee.restapi.errorhandling.LoginFailedException
import com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.jpa.PasswordResetKeyRepository
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import java.util.*

@Service
@Validated
class UserServiceImpl(
    val userRepository: UserRepository,
    val settingsService: SettingsService,
    val userDetailsRepository: UserDetailsRepository,
    val passwordEncoder: BCryptPasswordEncoder,
    val passwordResetKeyRepository: PasswordResetKeyRepository,
    val emailService: EmailService,
    val qrCodeService: QrCodeService,
    val qrCodeGeneratorService: QrCodeGeneratorService,
) : UserService {
    private val logger = KotlinLogging.logger { }

    override fun register(user: UserRegisterRequest): String {
        // register by admin
        if (user.password == null && user.userDetails.email != null) {
            val temporaryPassword = UUID.randomUUID().toString().substring(0, 5)
            val newUser = user.copy(password = temporaryPassword)
            val registerResult =
                userRepository.register(
                    newUser.requestToDomain(passwordEncoder),
                    newUser.userDetails.requestToDomain(),
                    user.language,
                )

            if (registerResult.contains("has been saved.")) {
                logger.info(registerResult)
                emailService.sendTemporaryPasswordToRegisteredUser(
                    to = user.userDetails.email,
                    name = user.userDetails.name,
                    password = temporaryPassword,
                    language = Language(LanguageEnum.valueOf(user.language).name),
                    schoolName = user.userDetails.schoolDetailsRequest.school.schoolName,
                )
            } else
                logger.error(registerResult)

            return registerResult
        }
        val reg = userRepository.register(
            user.requestToDomain(passwordEncoder),
            user.userDetails.requestToDomain(),
            user.language,
        )
        if (reg.contains("saved but mail not sent.")) {
            logger.error(reg)
        } else {
            logger.info(reg)
        }
        return reg
    }

    override fun saveGuest(language: String): UserDetailsDto {
        val guestNameOrPassword = UUID.randomUUID().toString() // Generate a random UUID string
        logger.info("Saving guest: {}", guestNameOrPassword)

        return userRepository.saveGuest(
            User(
                userName = UserName(guestNameOrPassword),
                password = Password(passwordEncoder.encode(guestNameOrPassword)),
                createdAt = LocalDateTime.now(),
                lastTimeOnline = LocalDateTime.now(),
                role = Role(
                    id = RoleId(null),
                    name = RoleName(""),
                ),
                isEnabled = UserEnabled(true),
            ),
            language,
        ).domainToDto()
    }

    override fun login(username: String, password: String): Optional<UserDetailsDto> {
        val user = userRepository.getUserByUsername(username)

        if (!user.isPresent) {
            throw UsernameNotFoundException("User not found.")
        }

        val encryptedPassword = user.get().user.password.value

        val userSettings = settingsService.getSettingsByUserDetailsId(user.get().id!!.value)

        return if (passwordEncoder.matches(password, encryptedPassword)) {
            userRepository.saveLastTimeOnline(
                user.get().user.copy(lastTimeOnline = LocalDateTime.now()),
            )
            user.optionalDomainToOptionalDto(userSettings)
        } else {
            throw LoginFailedException("Login failed. Invalid password.")
        }
    }

    //    @PreAuthorize("hasRole('SENSEI')")
    override fun getUserById(id: Long): UserDto =
        userRepository.getUserById(id).domainToDto()

    override fun getAllUsers(): List<UserDto>? =
        userRepository.getAllUsers()?.map { it.domainToDto() }

    @Transactional
    override fun changePassword(userChangePasswordRequest: UserChangePasswordRequest): String {
        val user = userRepository.getUserById(userId = userChangePasswordRequest.userId)

        val checkIfValidKey = passwordResetKeyRepository.checkIfKeyIsValidForUser(
            userChangePasswordRequest.userId,
            userChangePasswordRequest.passwordResetKey!!,
        )
        if (checkIfValidKey == "Reset Key is Valid") {
            val isUpdated = userRepository.updateUserPassword(
                user.id?.value!!,
                passwordEncoder.encode(userChangePasswordRequest.newPassword),
            )
            return if (isUpdated) {
                if (passwordResetKeyRepository.updatePasswordResetKeyIsUsed(
                        userId = userChangePasswordRequest.userId,
                        isUsed = true,
                    )
                ) {
                    "Password is updated."
                } else {
                    "Password is not updated."
                }
            } else {
                "Password is not updated."
            }
        } else {
            return checkIfValidKey
        }
    }

    override fun deleteUser(userId: Long): String {
        val userDetails = userDetailsRepository.getUserDetailsByUserId(userId)
        val qr = qrCodeService.getQrCodeForUser(userDetails.user.id!!)

        qrCodeGeneratorService.deleteQRCodeImage(qrCode = qr)

        return userRepository.deleteUser(userId)
    }

    override fun setUserEnableStatus(userId: Long, enabled: Boolean): String {
        val user = userRepository.getUserById(userId = userId)
        return userRepository.setUserEnableStatus(user.copy(isEnabled = UserEnabled(enabled)))
    }

    override fun getUserDetailsByTrainingLevelClassification(t: TrainingCalendarRequest): List<UserDetailsDto> =
        userDetailsRepository.getUserDetailsByTrainingLevelClassification(t).map { it.domainToDto() }
}
