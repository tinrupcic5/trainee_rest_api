package com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.stringToDate
import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.training.details.calendar.TrainingCalendarService
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarDto
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendar
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendarId
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingStatus
import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/training/calendar/")
class TrainingCalendarController(
    val trainingService: TrainingCalendarService,
) {

    @PutMapping("{trainingCalendarId}/{trainingStatus}")
    fun updateTrainingCalendar(
        @PathVariable trainingCalendarId: Long,
        @PathVariable trainingStatus: Boolean
    ): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK)
            .body(
                trainingService.updateTraining(
                    TrainingCalendarId(trainingCalendarId), TrainingStatus(trainingStatus)
                ).toMessageBody()
            )

    @GetMapping("{trainingDetailsId}/{date}")
    fun getTrainingCalendar(
        @PathVariable trainingDetailsId: Long,
        @PathVariable date: String,
    ): ResponseEntity<TrainingCalendarDto> =
        ResponseEntity.status(HttpStatus.OK)
            .body(
                trainingService.getTrainingCalendarByTrainingId(trainingDetailsId, date.stringToDate())
            )
}
