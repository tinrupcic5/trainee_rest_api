package com.bitroot.trainee.restapi.domain.training

import com.bitroot.trainee.restapi.appsettings.stringToDate
import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa.TrainingLevelRepository
import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevelClassificationEnum
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.jpa.TrainingRepository
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web.TrainingDto
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web.TrainingRequest
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web.toDomain
import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingDetails
import com.bitroot.trainee.restapi.domain.training.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa.TrainingCalendarRepository
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendar
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.training.details.participation.TrainingParticipationService
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.TrainingParticipationDto
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.TrainingParticipationRequest
import com.bitroot.trainee.restapi.domain.user.UserService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.*

@Service
@Validated
class TrainingServiceImpl(
    val trainingRepository: TrainingRepository,
    val userService: UserService,
    val trainingCalendarRepository: TrainingCalendarRepository,
    val trainingParticipationService: TrainingParticipationService,
    val trainingLevelRepository: TrainingLevelRepository,
) : TrainingService {

    private val logger = KotlinLogging.logger { }

    override fun getAllTrainingForToday(): Set<TrainingDetails> =
        trainingRepository.getAllTrainingForToday()

    override fun getAllTrainingForDate(
        userDetailsId: Long,
        schoolDetailsId: Long,
        date: String,
    ): Optional<List<TrainingDto>> {
        val trainingList =
            trainingRepository.getAllTrainingForDate(schoolDetailsId, date.stringToDate()).orElse(emptyList())

        val trainingDtoList = trainingList.map { training ->
            val trainingCalendars: List<TrainingCalendar> =
                trainingCalendarRepository.getTrainingCalendarByTrainingDetailsIdAndDate(
                    training.id.value!!,
                    date.stringToDate(),
                )

            val participation: List<TrainingParticipationDto> =
                trainingCalendars.flatMap { calendar ->
                    trainingParticipationService.getParticipantsForTheDate(
                        calendar.trainingDetails.id.value!!,
                        date,
                        training.schoolDetails.id?.value!!,
                    ).filter { it.userDetails.id == userDetailsId }
                }

            val latestTrainingCalendar = trainingCalendars.maxByOrNull { it.trainingDate }

            training.toDto(
                trainingStatus = latestTrainingCalendar?.trainingStatus?.value,
                participationDto = participation,
                trainingCalendarId = latestTrainingCalendar?.id?.value
            )
        }

        return Optional.of(trainingDtoList)
    }

    override fun getTrainingById(
        trainingId: Long,
        userDetailsId: Long,
        schoolDetailsId: Long,
        date: String
    ): TrainingDto =
        getAllTrainingForDate(userDetailsId, schoolDetailsId, date).get().filter { it.id == trainingId }.first()

    override fun saveDatesUntilEndDateForEveryWeek(t: TrainingCalendarRequest): String {
        trainingCalendarRepository.saveDatesUntilEndDateForEveryWeek(t)

        // get trainingCalendar Ids where dates are from and end , and where trainingDetails id is
        val trainingCalendarIds =
            trainingCalendarRepository.getTrainingCalendarByTrainingDetailsIdAndDateFromTo(
                t.trainingDetailsId,
                from = t.startDate,
                to = t.endDate,
            )

        val training = trainingRepository.getTrainingByTrainingDetailsId(t.trainingDetailsId)

        val trainingLevel = training.trainingLevel

        if (trainingLevel.trainingLevelClassificationName!!.value == TrainingLevelClassificationEnum.ALL.name) {
            logger.info(
                "saveDatesUntilEndDateForEveryWeek => Training Level Classification : {}",
                TrainingLevelClassificationEnum.ALL.name,
            )
            val listOfTrainingLevelClassification = listOf(
                trainingLevelRepository.getTrainingLevelByName(TrainingLevelClassificationEnum.ADULTS.name).id?.value!!,
                trainingLevelRepository.getTrainingLevelByName(TrainingLevelClassificationEnum.BEGINNERS.name).id?.value!!,
            )
            val userWithTrainingLevelClassification =
                userService.getUserDetailsByTrainingLevelClassification(
                    t.copy(
                        listOfTrainingLevelClassification = listOfTrainingLevelClassification,
                    ),
                )

            for (userDetails in userWithTrainingLevelClassification) {
                for (calendarId in trainingCalendarIds) {
                    trainingParticipationService.save(
                        TrainingParticipationRequest(
                            trainingCalendarId = calendarId.id.value!!,
                            userId = userDetails.user.id!!,
                            attendedStatus = true,

                            ),
                    )
                }
            }
        }
        logger.info(
            "saveDatesUntilEndDateForEveryWeek => Training Level Classification : {}",
            trainingLevel.trainingLevelClassificationName.value!!,
        )
        val userWithTrainingLevelClassification =
            userService.getUserDetailsByTrainingLevelClassification(
                t.copy(
                    listOfTrainingLevelClassification = listOf(
                        trainingLevelRepository.getTrainingLevelByName(
                            trainingLevel.trainingLevelClassificationName.value,
                        ).id?.value!!,
                    ),
                ),
            )

        for (userDetails in userWithTrainingLevelClassification) {
            for (calendarId in trainingCalendarIds) {
                trainingParticipationService.save(
                    TrainingParticipationRequest(
                        trainingCalendarId = calendarId.id.value!!,
                        userId = userDetails.user.id!!,
                        attendedStatus = true,
                        ),
                )
            }
        }

        return "saved!!"
    }

    override fun saveTraining(trainingRequest: TrainingRequest): String? =
        trainingRepository.saveTraining(trainingRequest.toDomain())

    override fun updateTraining(trainingRequest: TrainingRequest): String? {
        val trainingDetails = trainingRepository.getTrainingByTrainingDetailsId(trainingRequest.id!!)
        return trainingRepository.saveTraining(
            trainingDetails.copy(
                trainingTime = trainingRequest.trainingTime
            )
        )
    }

    override fun getTrainingByTrainingDetailsId(trainingDetailsId: Long): TrainingDetails =
        trainingRepository.getTrainingByTrainingDetailsId(trainingDetailsId)

    override fun getListOfTrainingByTrainingDetailsId(trainingDetailsId: List<Long>): List<TrainingDetails> =
        trainingRepository.getListOfTrainingByTrainingDetailsId(trainingDetailsId)
}
