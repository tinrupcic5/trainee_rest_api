package com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.web.TrainingLevelDto
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.web.SchoolDetailsDto
import com.bitroot.trainee.restapi.domain.settings.adapter.outgoing.web.SettingsDto
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web.UserDto
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDetailsDto(
    val id: Long?,
    val user: UserDto,
    val name: String,
    val lastName: String,
    val email: String?,
    val phoneNumber: String?,
    val schoolDetails: SchoolDetailsDto,
    val trainingLevel: TrainingLevelDto?,
    val settings: SettingsDto? = null,
)
