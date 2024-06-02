package com.bitroot.trainee.restapi.domain.training.details.participation

import com.bitroot.trainee.restapi.appsettings.stringToDate
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.jpa.TrainingRepository
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa.TrainingCalendarRepository
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.jpa.TrainingParticipationRepository
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.TrainingParticipationDto
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.TrainingParticipationRequest
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.toDomain
import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.AttendedStatus
import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.TrainingParticipation
import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.TrainingParticipationId
import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsRepository
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class TrainingParticipationServiceImpl(
    val trainingParticipationRepository: TrainingParticipationRepository,
    val userDetailsRepository: UserDetailsRepository,
    val trainingCalendarRepository: TrainingCalendarRepository,
    val trainingRepository: TrainingRepository,

) : TrainingParticipationService {
    override fun save(trainingParticipationRequest: TrainingParticipationRequest): String {
        val userDetails = userDetailsRepository.getUserDetailsByUserId(trainingParticipationRequest.userId)
        val calendarDetails =
            trainingCalendarRepository.getTrainingCalendarById(trainingParticipationRequest.trainingCalendarId)
        return trainingParticipationRepository.save(
            trainingParticipationRequest.toDomain(
                trainingCalendar = calendarDetails,
                userDetails = userDetails,
            ),
        )
    }

    override fun getParticipantsForTheDate(
        trainingDetailsId: Long,
        date: String,
        schoolDetailsId: Long,
    ): List<TrainingParticipationDto> =
        trainingParticipationRepository.getParticipantsForTheDate(
            trainingDetailsId,
            date.stringToDate(),
            schoolDetailsId,
        ).map { it.toDto() }

    override fun updateParticipationForUserDetailsId(userDetails: UserDetails) {
        val trainingParticipationList =
            trainingParticipationRepository.getParticipationForUSerDetailsId(userDetails.id!!.value)

        trainingParticipationRepository.delete(trainingParticipationList.map { it.id.value!! })

        val trd = trainingRepository.getTrainingByTrainingLevel(userDetails.trainingLevel?.id?.value!!)

        val trainingCal =
            trainingCalendarRepository.getTrainingCalendarByListOfTrainingDetailsIds(trd.map { it.id.value!! })

        trainingCal.forEach {
            trainingParticipationRepository.save(
                TrainingParticipation(
                    id = TrainingParticipationId(null),
                    trainingCalendar = it,
                    userDetails = userDetails,
                    attendedStatus = AttendedStatus(true),
                ),
            )
        }
    }
}
