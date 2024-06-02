package com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendar
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendarId
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class TrainingCalendarRepositoryImpl(
    private val trainingCalendarEntityJpaRepository: TrainingCalendarEntityJpaRepository,
) : TrainingCalendarRepository {

    override fun saveDatesUntilEndDateForEveryWeek(trainingCalendarRequest: TrainingCalendarRequest): String {
        val rows = trainingCalendarEntityJpaRepository.saveDatesUntilEndDateForEveryWeek(
            trainingCalendarRequest.startDate,
            trainingCalendarRequest.endDate,
            trainingCalendarRequest.trainingDetailsId,
            trainingCalendarRequest.trainingStatus,
        )
        return "Affected rows: $rows"
    }

    override fun getTrainingCalendarById(trainingCalendarId: Long): TrainingCalendar =
        trainingCalendarEntityJpaRepository.getTrainingCalendarById(trainingCalendarId).toDomain()

    override fun getTrainingCalendarByListOfId(trainingCalendarIdList: List<Long>): List<TrainingCalendar> =
        trainingCalendarEntityJpaRepository.getTrainingCalendarByListOfId(trainingCalendarIdList).map { it.toDomain() }

    override fun getTrainingCalendarByListOfTrainingDetailsIds(trainingDetailsIdList: List<Long>): List<TrainingCalendar> =
        trainingCalendarEntityJpaRepository.getTrainingCalendarByListOfTrainingDetailsIds(trainingDetailsIdList)
            .map { it.toDomain() }

    override fun getTrainingCalendarByTrainingDetailsIdAndDate(trainingDetailsId: Long, date: Date) =
        trainingCalendarEntityJpaRepository.getTrainingCalendarByTrainingDetailsIdAndDate(trainingDetailsId, date)
            .map { it.toDomain() }

    override fun getTrainingCalendarByTrainingDetailsIdAndDateFromTo(
        trainingDetailsId: Long,
        from: Date,
        to: Date,
    ): List<TrainingCalendar> =
        trainingCalendarEntityJpaRepository.getTrainingCalendarByTrainingDetailsIdAndDateFromTo(
            trainingDetailsId,
            from,
            to,
        ).map { it.toDomain() }

    override fun delete(trainingCalendarByListOfId: List<Long>): String {
        trainingCalendarEntityJpaRepository.deleteAllById(trainingCalendarByListOfId)
        return "Training calendar Id's deleted."
    }

    override fun updateTraining(trainingCalendarId: TrainingCalendarId, trainingStatus: TrainingStatus): String {
        trainingCalendarEntityJpaRepository.updateTraining(trainingCalendarId.value!!, trainingStatus.value)
        val trainingCal = trainingCalendarEntityJpaRepository.getTrainingCalendarById(trainingCalendarId.value)
        return "Training with id ${trainingCal.id} is updated"
    }

    override fun getTrainingCalendarByTrainingId(trainingDetailsId: Long, date: Date): TrainingCalendar =
        trainingCalendarEntityJpaRepository.getTrainingCalendarByTrainingId(trainingDetailsId, date).toDomain()
}
