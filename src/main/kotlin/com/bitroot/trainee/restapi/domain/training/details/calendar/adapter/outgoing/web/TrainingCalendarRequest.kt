package com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web

import java.util.Date

data class TrainingCalendarRequest(
    val startDate: Date, // training date
    val endDate: Date, // until the date
    val trainingDetailsId: Long,
    val trainingStatus: Boolean,
    val listOfTrainingLevelClassification: List<Long>? = null,
    val schoolDetailsId: Long,
)
