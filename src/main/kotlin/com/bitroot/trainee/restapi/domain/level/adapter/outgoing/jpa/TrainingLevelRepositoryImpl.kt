package com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevel
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class TrainingLevelRepositoryImpl(
    val trainingLevelEntityJpaRepository: TrainingLevelEntityJpaRepository,
) : TrainingLevelRepository {
    override fun save(trainingLevelClassification: String): TrainingLevel =
        trainingLevelEntityJpaRepository.save(
            TrainingLevelEntity(
                trainingLevelClassification = trainingLevelClassification,
            ),
        ).toDomain()

    override fun getTrainingLevels(): List<TrainingLevel> =
        trainingLevelEntityJpaRepository.findAll().map { it.toDomain() }

    override fun getTrainingLevelByName(trainingLevel: String): TrainingLevel =
        trainingLevelEntityJpaRepository.getTrainingLevelByName(trainingLevel).toDomain()

    override fun getTrainingLevelById(trainingLevel: Long): TrainingLevel =
        trainingLevelEntityJpaRepository.getReferenceById(trainingLevel).toDomain()
}
