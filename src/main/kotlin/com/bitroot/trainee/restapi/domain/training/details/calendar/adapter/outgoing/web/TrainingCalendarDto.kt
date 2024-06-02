package com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web.TrainingDto

data class TrainingCalendarDto(
    val id: Long?,
    val trainingDate: String,
    val trainingDetails: TrainingDto,
    val trainingStatus: Boolean, // canceled / active
)
