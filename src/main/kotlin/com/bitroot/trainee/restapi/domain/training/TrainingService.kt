package com.bitroot.trainee.restapi.domain.training

import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web.TrainingDto
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web.TrainingRequest
import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingDetails
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import java.util.*

interface TrainingService {
    fun getAllTrainingForToday(): Set<TrainingDetails>
    fun getAllTrainingForDate(userDetailsId: Long, schoolDetailsId: Long, date: String): Optional<List<TrainingDto>>
    fun saveDatesUntilEndDateForEveryWeek(t: TrainingCalendarRequest): String
    fun saveTraining(trainingRequest: TrainingRequest): String?
    fun updateTraining(trainingRequest: TrainingRequest): String?
    fun getTrainingByTrainingDetailsId(trainingDetailsId: Long): TrainingDetails
    fun getListOfTrainingByTrainingDetailsId(trainingDetailsId: List<Long>): List<TrainingDetails>
    fun getTrainingById(
        trainingId: Long,
        userDetailsId: Long,
        schoolDetailsId: Long,
        date: String,
    ): TrainingDto
}
