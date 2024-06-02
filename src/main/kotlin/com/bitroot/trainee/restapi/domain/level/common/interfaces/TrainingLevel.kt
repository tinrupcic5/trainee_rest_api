package com.bitroot.trainee.restapi.domain.level.common.interfaces

import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa.TrainingLevelEntity

data class TrainingLevel(
    val id: TrainingLevelId? = null,
    val trainingLevelClassificationName: TrainingLevelClassificationName?,
)

fun TrainingLevel.toEntity(): TrainingLevelEntity =
    TrainingLevelEntity(
        id = this.id?.value,
        trainingLevelClassification = this.trainingLevelClassificationName?.value,
    )
