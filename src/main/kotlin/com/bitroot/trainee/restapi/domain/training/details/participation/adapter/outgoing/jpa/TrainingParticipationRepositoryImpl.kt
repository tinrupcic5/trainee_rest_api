package com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.TrainingParticipation
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class TrainingParticipationRepositoryImpl(
    private val trainingParticipationEntityJpaRepository: TrainingParticipationEntityJpaRepository,
) : TrainingParticipationRepository {
    override fun getAllParticipationForToday(): Set<TrainingParticipation> =
        trainingParticipationEntityJpaRepository.getAllParticipationForToday().map { it.toDomain() }.toSet()

    override fun getParticipationById(trainingParticipationId: Long): TrainingParticipation =
        trainingParticipationEntityJpaRepository.getReferenceById(trainingParticipationId).toDomain()

    override fun getParticipationForUSerDetailsId(userDetailsId: Long): List<TrainingParticipation> =
        trainingParticipationEntityJpaRepository.getParticipationForUSerDetailsId(userDetailsId).map { it.toDomain() }

    override fun save(trainingParticipation: TrainingParticipation): String {
        val trainingParticipationEntity =
            trainingParticipationEntityJpaRepository.saveParticipants(
                trainingCalendarId = trainingParticipation.trainingCalendar.id.value!!,
                userDetailsId = trainingParticipation.userDetails.id!!.value,
                attendedStatus = trainingParticipation.attendedStatus.value,
            )
        return "TrainingParticipation saved. Training calendar id: $trainingParticipationEntity"
    }

    override fun getParticipantsForTheDate(
        trainingDetailsId: Long,
        date: Date,
        schoolDetailsId: Long,
    ): List<TrainingParticipation> =
        trainingParticipationEntityJpaRepository.getParticipantsForTheDate(trainingDetailsId, date, schoolDetailsId)
            .map { it.toDomain() }

    override fun findByTrainingCalendarIdAndUserDetailsId(
        trainingCalendarId: Long,
        userDetailsId: Long,
    ): TrainingParticipation? =
        trainingParticipationEntityJpaRepository.findByTrainingCalendarIdAndUserDetailsId(
            trainingCalendarId,
            userDetailsId,
        )

    override fun delete(trainingParticipationList: List<Long>): String {
        trainingParticipationEntityJpaRepository.deleteAllById(trainingParticipationList)
        return "Training Participation deleted."
    }
}
