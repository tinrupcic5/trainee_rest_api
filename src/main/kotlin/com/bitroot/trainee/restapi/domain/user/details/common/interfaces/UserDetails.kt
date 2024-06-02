package com.bitroot.trainee.restapi.domain.user.details.common.interfaces

import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.web.toDto
import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevel
import com.bitroot.trainee.restapi.domain.level.common.interfaces.toEntity
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.domainToEntity
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.toRequest
import com.bitroot.trainee.restapi.domain.settings.adapter.outgoing.web.SettingsDto
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Settings
import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.common.interfaces.domainToEntity
import com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web.UserDetailsRegisterRequest
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsEntity
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.web.UserDetailsDto
import java.util.Optional

data class UserDetails(
    val id: UserDetailsId? = null,
    val user: User,
    val name: UserDetailsName,
    val lastName: UserDetailsLastName,
    val email: UserDetailsEmail,
    val phoneNumber: UserDetailsPhoneNumber?,
    val schoolDetails: SchoolDetails,
    val trainingLevel: TrainingLevel?,
)

fun UserDetails.toRequest(): UserDetailsRegisterRequest =
    UserDetailsRegisterRequest(
        id = this.id?.value,
        name = this.name.value,
        lastName = this.lastName.value,
        email = this.email.value,
        phoneNumber = this.phoneNumber?.value,
        schoolDetailsRequest = this.schoolDetails.toRequest(),
        trainingLevel = this.trainingLevel?.trainingLevelClassificationName?.value,

    )
fun UserDetails.domainToEntity(): UserDetailsEntity =
    UserDetailsEntity(
        id = this.id?.value,
        user = this.user.domainToEntity(),
        name = this.name.value,
        lastName = this.lastName.value,
        email = this.email.value,
        phoneNumber = this.phoneNumber?.value,
        schoolDetailsEntity = this.schoolDetails.domainToEntity(),
        trainingLevelEntity = this.trainingLevel?.toEntity(),

    )

fun UserDetails.domainToDto(): UserDetailsDto =
    UserDetailsDto(
        id = this.id?.value,
        user = this.user.domainToDto(),
        name = this.name.value,
        lastName = this.lastName.value,
        email = this.email.value,
        phoneNumber = this.phoneNumber?.value,
        schoolDetails = this.schoolDetails.domainToDto(),
        trainingLevel = this.trainingLevel?.toDto(),
    )

fun Optional<UserDetails>.optionalDomainToOptionalDto(settings: Settings): Optional<UserDetailsDto> =
    this.map { userDetails ->
        UserDetailsDto(
            id = userDetails.id?.value,
            user = userDetails.user.domainToDto(),
            name = userDetails.name.value,
            lastName = userDetails.lastName.value,
            email = userDetails.email.value,
            phoneNumber = userDetails.phoneNumber?.value,
            schoolDetails = userDetails.schoolDetails.domainToDto(),
            trainingLevel = userDetails.trainingLevel?.toDto(),
            settings = SettingsDto(
                id = settings.id?.value!!,
                language = settings.language.value,
            ),
        )
    }
