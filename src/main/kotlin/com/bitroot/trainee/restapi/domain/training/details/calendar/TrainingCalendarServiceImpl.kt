package com.bitroot.trainee.restapi.domain.training.details.calendar

import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa.TrainingCalendarRepository
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarDto
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendarId
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingStatus
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.toDto
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.*

@Service
@Validated
class TrainingCalendarServiceImpl(
    val trainingRepository: TrainingCalendarRepository,

    ) : TrainingCalendarService {
    override fun saveDatesUntilEndDateForEveryWeek(trainingCalendarRequest: TrainingCalendarRequest): String =
        trainingRepository.saveDatesUntilEndDateForEveryWeek(trainingCalendarRequest)

    override fun updateTraining(trainingId: TrainingCalendarId, trainingStatus: TrainingStatus): String =
        trainingRepository.updateTraining(trainingId, trainingStatus)

    override fun getTrainingCalendarByTrainingId(trainingDetailsId: Long, date: Date): TrainingCalendarDto =
        trainingRepository.getTrainingCalendarByTrainingId(trainingDetailsId, date).toDto()
}
