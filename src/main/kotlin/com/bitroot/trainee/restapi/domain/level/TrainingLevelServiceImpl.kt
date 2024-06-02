package com.bitroot.trainee.restapi.domain.level

import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa.TrainingLevelRepository
import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevel
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class TrainingLevelServiceImpl(
    val trainingLevelRepository: TrainingLevelRepository,
) : TrainingLevelService {
    override fun save(trainingLevelClassification: String): TrainingLevel =
        trainingLevelRepository.save(trainingLevelClassification)

    override fun getTrainingLevels(): List<TrainingLevel> =
        trainingLevelRepository.getTrainingLevels()
}
