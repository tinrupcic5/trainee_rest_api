package com.bitroot.trainee.restapi.domain.training.details.calendar

import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarDto
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendarId
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingStatus
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserId
import java.util.*

interface TrainingCalendarService {
    fun saveDatesUntilEndDateForEveryWeek(trainingCalendarRequest: TrainingCalendarRequest): String
    fun updateTraining(trainingId: TrainingCalendarId, trainingStatus: TrainingStatus, userId: UserId): String
    fun getTrainingCalendarByTrainingId(trainingDetailsId: Long, date: Date): TrainingCalendarDto
}
