package com.bitroot.trainee.restapi.domain.training.common.interfaces

import java.time.LocalTime

enum class TrainingTypes {
    MORNING,
    MIDDAY,
    AFTERNOON,
}

fun getCurrentTrainingType(currentTime: LocalTime): TrainingTypes {
    return when {
        currentTime.isBefore(LocalTime.of(12, 0)) -> TrainingTypes.MORNING
        currentTime.isBefore(LocalTime.of(17, 0)) -> TrainingTypes.MIDDAY
        else -> TrainingTypes.AFTERNOON
    }
}
