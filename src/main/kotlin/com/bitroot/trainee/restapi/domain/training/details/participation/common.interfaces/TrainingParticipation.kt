package com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces

import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendar
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.toEntity
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.jpa.TrainingParticipationEntity
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.TrainingParticipationDto
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToEntity

data class TrainingParticipation(
    val id: TrainingParticipationId,
    val trainingCalendar: TrainingCalendar,
    val userDetails: UserDetails,
    val attendedStatus: AttendedStatus,
)

fun TrainingParticipation.toDto(): TrainingParticipationDto =
    TrainingParticipationDto(
        id = this.id.value,
        trainingCalendar = this.trainingCalendar.toDto(),
        userDetails = this.userDetails.domainToDto(),
        attendedStatus = this.attendedStatus.value,
    )

fun TrainingParticipation.toEntity(): TrainingParticipationEntity =
    TrainingParticipationEntity(
        id = this.id.value,
        trainingCalendar = this.trainingCalendar.toEntity(),
        user = this.userDetails.domainToEntity(),
        attendedStatus = this.attendedStatus.value,
    )
