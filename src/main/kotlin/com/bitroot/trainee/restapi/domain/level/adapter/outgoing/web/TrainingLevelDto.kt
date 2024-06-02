package com.bitroot.trainee.restapi.domain.level.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevel

data class TrainingLevelDto(
    val id: Long? = null,
    val trainingLevelClassification: String?,
)

fun TrainingLevel.toDto(): TrainingLevelDto =
    TrainingLevelDto(
        id = this.id?.value,
        trainingLevelClassification = this.trainingLevelClassificationName?.value,
    )
