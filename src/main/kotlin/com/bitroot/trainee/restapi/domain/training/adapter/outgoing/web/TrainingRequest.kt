package com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevel
import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevelClassificationName
import com.bitroot.trainee.restapi.domain.school.details.adapter.incoming.web.SchoolDetailsRequest
import com.bitroot.trainee.restapi.domain.school.details.adapter.incoming.web.requestToDomain
import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingDetails
import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingId
import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingName
import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingType
import com.bitroot.trainee.restapi.domain.training.common.interfaces.getCurrentTrainingType
import com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web.UserDetailsRegisterRequest
import com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web.requestToDomain
import java.sql.Time
import java.time.LocalDateTime

data class TrainingRequest(
    val id: Long? = null,
    val name: String,
    val type: String,
    val schoolDetails: SchoolDetailsRequest,
    val createdBy: UserDetailsRegisterRequest,
    val createdAt: LocalDateTime,
    val trainingTime: Time,
    val trainingStatus: Boolean,
    val trainingLevel: String,
)

fun TrainingRequest.toDomain(): TrainingDetails =
    TrainingDetails(
        id = TrainingId(this.id),
        name = TrainingName(this.name),
        type = TrainingType(getCurrentTrainingType(this.trainingTime.toLocalTime()).name),
        schoolDetails = this.schoolDetails.requestToDomain(),
        createdBy = this.createdBy.requestToDomain(),
        createdAt = LocalDateTime.now(),
        trainingTime = this.trainingTime,
        trainingLevel = TrainingLevel(trainingLevelClassificationName = TrainingLevelClassificationName(this.trainingLevel)),
    )
