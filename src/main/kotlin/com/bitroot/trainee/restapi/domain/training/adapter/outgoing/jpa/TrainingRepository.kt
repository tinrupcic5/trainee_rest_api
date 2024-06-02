package com.bitroot.trainee.restapi.domain.training.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingDetails
import java.util.*

interface TrainingRepository {

    fun getAllTrainingForToday(): Set<TrainingDetails>
    fun getAllTrainingForDate(schoolDetailsId: Long, date: Date): Optional<List<TrainingDetails>>
    fun saveTraining(trainingDetails: TrainingDetails): String?
    fun getSchoolByTrainingDetailsId(trainingDetailsId: Long): TrainingDetails
    fun getTrainingByTrainingDetailsId(trainingDetailsId: Long): TrainingDetails
    fun getListOfTrainingByTrainingDetailsId(trainingDetailsId: List<Long>): List<TrainingDetails>
    fun getTrainingByTrainingLevel(trainingLevel: Long): List<TrainingDetails>
}
