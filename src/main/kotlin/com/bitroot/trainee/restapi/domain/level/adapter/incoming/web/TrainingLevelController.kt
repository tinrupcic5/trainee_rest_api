package com.bitroot.trainee.restapi.domain.level.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.level.TrainingLevelService
import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.web.TrainingLevelDto
import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.web.toDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/training-level")
class TrainingLevelController(
    val trainingLevelService: TrainingLevelService,
) {
    @GetMapping
    fun getTrainingLevels(): ResponseEntity<List<TrainingLevelDto>?> =
        ResponseEntity.status(HttpStatus.OK).body(
            trainingLevelService.getTrainingLevels().map { it.toDto() },
        )
}
