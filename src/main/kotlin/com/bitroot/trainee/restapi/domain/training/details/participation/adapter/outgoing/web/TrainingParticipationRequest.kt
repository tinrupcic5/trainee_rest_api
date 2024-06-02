package com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendar
import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.AttendedStatus
import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.TrainingParticipation
import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.TrainingParticipationId
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails

data class TrainingParticipationRequest(
    val id: Long? = null,
    val trainingCalendarId: Long,
    val userId: Long,
    val attendedStatus: Boolean,
)

fun TrainingParticipationRequest.toDomain(
    trainingCalendar: TrainingCalendar,
    userDetails: UserDetails,
): TrainingParticipation =
    TrainingParticipation(
        id = TrainingParticipationId(this.id),
        trainingCalendar = trainingCalendar,
        userDetails = userDetails,
        attendedStatus = AttendedStatus(this.attendedStatus),
    )
