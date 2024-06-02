package com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.web.TrainingLevelDto
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.web.SchoolDetailsDto
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarDto
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.TrainingParticipationDto
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.web.UserDetailsDto
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TrainingDto(
    val id: Long?,
    val name: String,
    val type: String,
    val schoolDetails: SchoolDetailsDto,
    val createdBy: UserDetailsDto,
    val createdAt: String,
    val trainingTime: String,
    val trainingStatus: Boolean? = null,
    val trainingLevel: TrainingLevelDto,
    val participation: List<TrainingParticipationDto>? = null,
    val trainingCalendarId: Long? = null,

    )
