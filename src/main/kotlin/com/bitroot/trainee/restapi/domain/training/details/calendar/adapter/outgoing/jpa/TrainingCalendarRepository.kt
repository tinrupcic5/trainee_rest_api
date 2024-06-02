package com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarDto
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendar
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendarId
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingStatus
import java.util.*

interface TrainingCalendarRepository {

    /**
     * Save training for exact date to the end of the date
     */
    fun saveDatesUntilEndDateForEveryWeek(trainingCalendarRequest: TrainingCalendarRequest): String
    fun getTrainingCalendarById(trainingCalendarId: Long): TrainingCalendar
    fun getTrainingCalendarByListOfId(trainingCalendarIdList: List<Long>): List<TrainingCalendar>
    fun getTrainingCalendarByListOfTrainingDetailsIds(trainingDetailsIdList: List<Long>): List<TrainingCalendar>
    fun getTrainingCalendarByTrainingDetailsIdAndDate(trainingDetailsId: Long, date: Date): List<TrainingCalendar>
    fun getTrainingCalendarByTrainingDetailsIdAndDateFromTo(trainingDetailsId: Long, from: Date, to: Date): List<TrainingCalendar>
    fun delete(trainingCalendarByListOfId: List<Long>): String
    fun updateTraining(trainingCalendarId: TrainingCalendarId, trainingStatus: TrainingStatus): String
    fun getTrainingCalendarByTrainingId(trainingDetailsId: Long, date: Date): TrainingCalendar
}
