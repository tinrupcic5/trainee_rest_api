package com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarDto
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.web.UserDetailsDto

data class TrainingParticipationDto(
    val id: Long? = null,
    val trainingCalendar: TrainingCalendarDto,
    val userDetails: UserDetailsDto,
    val attendedStatus: Boolean,
)
