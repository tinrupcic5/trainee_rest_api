package com.bitroot.trainee.restapi.domain.training.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingDetails
import com.bitroot.trainee.restapi.domain.training.common.interfaces.toEntity
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class TrainingRepositoryImpl(
    private val trainingEntityJpaRepository: TrainingEntityJpaRepository,
) : TrainingRepository {
    override fun getAllTrainingForToday(): Set<TrainingDetails> =
        trainingEntityJpaRepository.getAllTrainingForToday().setToDomain()

    override fun getAllTrainingForDate(schoolDetailsId: Long, date: Date): Optional<List<TrainingDetails>> =
        trainingEntityJpaRepository.getAllTrainingForDate(schoolDetailsId, date)
            .map { it -> it.map { it.toDomain() } }

    override fun saveTraining(trainingDetails: TrainingDetails): String? {
        val trainingEntity = trainingEntityJpaRepository.save(trainingDetails.toEntity())
        return "Training ${trainingEntity.name} saved or updated."
    }

    override fun getSchoolByTrainingDetailsId(trainingDetailsId: Long): TrainingDetails =
        trainingEntityJpaRepository.getReferenceById(trainingDetailsId).toDomain()

    override fun getTrainingByTrainingDetailsId(trainingDetailsId: Long): TrainingDetails =
        trainingEntityJpaRepository.getReferenceById(trainingDetailsId).toDomain()

    override fun getListOfTrainingByTrainingDetailsId(trainingDetailsId: List<Long>): List<TrainingDetails> =
        trainingEntityJpaRepository.getListOfTrainingByTrainingDetailsId(trainingDetailsId).map { it.toDomain() }

    override fun getTrainingByTrainingLevel(trainingLevel: Long): List<TrainingDetails> =
        trainingEntityJpaRepository.getTrainingByTrainingLevel(trainingLevel).map { it.toDomain() }
}
