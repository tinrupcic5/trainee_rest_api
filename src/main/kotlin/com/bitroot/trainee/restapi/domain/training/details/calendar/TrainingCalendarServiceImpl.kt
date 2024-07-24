package com.bitroot.trainee.restapi.domain.training.details.calendar

import com.bitroot.trainee.restapi.domain.notification.adapter.incoming.web.NotificationRequest
import com.bitroot.trainee.restapi.domain.notification.adapter.incoming.web.toDomain
import com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.jpa.NotificationRepository
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.SchoolDetailsRepository
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa.TrainingCalendarRepository
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarDto
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendarId
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingStatus
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserId
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsRepository
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.*

@Service
@Validated
class TrainingCalendarServiceImpl(
    val trainingRepository: TrainingCalendarRepository,
    val notificationRepository: NotificationRepository,
    val schoolDetailsRepository: SchoolDetailsRepository,
    val userDetailsRepository: UserDetailsRepository,
) : TrainingCalendarService {
    override fun saveDatesUntilEndDateForEveryWeek(trainingCalendarRequest: TrainingCalendarRequest): String =
        trainingRepository.saveDatesUntilEndDateForEveryWeek(trainingCalendarRequest)

    override fun updateTraining(
        trainingId: TrainingCalendarId,
        trainingStatus: TrainingStatus,
        userId: UserId,
    ): String {
        val userDetails = userDetailsRepository.getUserDetailsByUserId(userId.value!!)
        val schoolDetails = schoolDetailsRepository.getSchoolDetailsById(userDetails.schoolDetails.id?.value!!)
        val training = trainingRepository.getTrainingCalendarById(trainingId.value!!)
        val message = if (training.trainingStatus.value) {
            "Trening za datum ${training.trainingDate} će se održati."
        } else {
            "Trening za datum ${training.trainingDate} je otkazan."
        }
        notificationRepository.saveOrUpdateNotification(
            NotificationRequest(
                schoolDetailsId = schoolDetails.id?.value!!,
                createdByUser = userDetails.user.id?.value!!,
                message = message,
            ).toDomain(schoolDetails, userDetails.user),
        )
        return trainingRepository.updateTraining(trainingId, trainingStatus)
    }

    override fun getTrainingCalendarByTrainingId(
        trainingDetailsId: Long,
        date: Date,
    ): TrainingCalendarDto = trainingRepository.getTrainingCalendarByTrainingId(trainingDetailsId, date).toDto()
}
