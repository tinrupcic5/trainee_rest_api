package com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevel

interface TrainingLevelRepository {

    fun save(trainingLevelClassification: String): TrainingLevel
    fun getTrainingLevels(): List<TrainingLevel>
    fun getTrainingLevelByName(trainingLevel: String): TrainingLevel
    fun getTrainingLevelById(trainingLevel: Long): TrainingLevel
}
