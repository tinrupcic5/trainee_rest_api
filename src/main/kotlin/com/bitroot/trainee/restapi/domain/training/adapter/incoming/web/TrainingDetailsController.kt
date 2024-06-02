package com.bitroot.trainee.restapi.domain.training.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.training.TrainingService
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web.TrainingDto
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web.TrainingRequest
import com.bitroot.trainee.restapi.domain.training.common.interfaces.setToDto
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/training")
class TrainingDetailsController(
    val trainingService: TrainingService,
) {
    @GetMapping("/{schoolDetailsId}")
    fun getAllTrainingForToday(@PathVariable schoolDetailsId: Long): ResponseEntity<Set<TrainingDto>> =
        ResponseEntity.status(HttpStatus.OK).body(
            trainingService.getAllTrainingForToday().setToDto(),
        )

    @GetMapping("/{userDetailsId}/{schoolDetailsId}/{date}")
    fun getAllTrainingForDate(
        @PathVariable userDetailsId: Long,
        @PathVariable schoolDetailsId: Long,
        @PathVariable date: String,
    ): ResponseEntity<Optional<List<TrainingDto>>> =
        ResponseEntity.status(HttpStatus.OK).body(
            trainingService.getAllTrainingForDate(userDetailsId, schoolDetailsId, date),
        )

    @GetMapping("/{trainingId}/{userDetailsId}/{schoolDetailsId}/{date}")
    fun getTrainingById(
        @PathVariable trainingId: Long,
        @PathVariable userDetailsId: Long,
        @PathVariable schoolDetailsId: Long,
        @PathVariable date: String,
    ): ResponseEntity<TrainingDto> =
        ResponseEntity.status(HttpStatus.OK).body(
            trainingService.getTrainingById(trainingId, userDetailsId, schoolDetailsId, date),
        )

    @PutMapping
    fun saveDatesUntilEndDateForEveryWeek(@RequestBody trainingCalendarRequest: TrainingCalendarRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK)
            .body(trainingService.saveDatesUntilEndDateForEveryWeek(trainingCalendarRequest).toMessageBody())

    @PostMapping
    fun saveTrainingForTheDate(@RequestBody trainingRequest: TrainingRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK)
            .body(trainingService.saveTraining(trainingRequest)?.toMessageBody())

    @PutMapping("/update")
    fun updateTraining(@RequestBody trainingRequest: TrainingRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK)
            .body(trainingService.saveTraining(trainingRequest)?.toMessageBody())
}
