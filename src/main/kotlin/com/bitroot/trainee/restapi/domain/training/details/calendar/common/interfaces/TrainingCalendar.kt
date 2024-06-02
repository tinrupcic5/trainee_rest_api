package com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces

import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingDetails
import com.bitroot.trainee.restapi.domain.training.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.training.common.interfaces.toEntity
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa.TrainingCalendarEntity
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarDto
import java.sql.Date

data class TrainingCalendar(
    val id: TrainingCalendarId,
    val trainingDate: Date,
    val trainingDetails: TrainingDetails,
    val trainingStatus: TrainingStatus,
)
fun TrainingCalendar.toEntity(): TrainingCalendarEntity =
    TrainingCalendarEntity(
        id = this.id.value,
        trainingDate = this.trainingDate,
        trainingDetails = this.trainingDetails.toEntity(),
        trainingStatus = this.trainingStatus.value,
    )

fun TrainingCalendar.toDto(): TrainingCalendarDto =
    TrainingCalendarDto(
        id = this.id.value,
        trainingDate = this.trainingDate.toString(),
        trainingDetails = this.trainingDetails.toDto(),
        trainingStatus = this.trainingStatus.value,
    )
