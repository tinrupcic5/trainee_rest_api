package com.bitroot.trainee.restapi.domain.training.details.calendar

import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarDto
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendarId
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingStatus
import java.util.*

interface TrainingCalendarService {
    fun saveDatesUntilEndDateForEveryWeek(trainingCalendarRequest: TrainingCalendarRequest): String
    fun updateTraining(trainingId: TrainingCalendarId, trainingStatus: TrainingStatus): String
    fun getTrainingCalendarByTrainingId(trainingDetailsId: Long, date: Date): TrainingCalendarDto
}
