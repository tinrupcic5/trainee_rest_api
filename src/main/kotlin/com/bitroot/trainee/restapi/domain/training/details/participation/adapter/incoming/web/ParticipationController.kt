package com.bitroot.trainee.restapi.domain.training.details.participation.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.training.details.participation.TrainingParticipationService
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.TrainingParticipationRequest
import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/participation")
class ParticipationController(
    val trainingParticipationService: TrainingParticipationService,
) {
    @PutMapping
    fun saveParticipation(@RequestBody request: TrainingParticipationRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK)
            .body(trainingParticipationService.save(request).toMessageBody())
}
