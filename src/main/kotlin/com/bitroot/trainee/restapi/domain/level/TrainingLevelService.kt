package com.bitroot.trainee.restapi.domain.level

import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevel

interface TrainingLevelService {
    fun save(trainingLevelClassification: String): TrainingLevel
    fun getTrainingLevels(): List<TrainingLevel>
}
