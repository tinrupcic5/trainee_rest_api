package com.bitroot.trainee.restapi.domain.training.common.interfaces

import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.web.toDto
import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevel
import com.bitroot.trainee.restapi.domain.level.common.interfaces.toEntity
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.domainToEntity
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.jpa.TrainingDetailsEntity
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.web.TrainingDto
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.TrainingParticipationDto
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToEntity
import java.sql.Time
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TrainingDetails(
    val id: TrainingId,
    val name: TrainingName,
    val type: TrainingType,
    val schoolDetails: SchoolDetails,
    val createdBy: UserDetails,
    val createdAt: LocalDateTime,
    val trainingTime: Time,
    val trainingLevel: TrainingLevel,
)

fun TrainingDetails.toEntity(): TrainingDetailsEntity =
    TrainingDetailsEntity(
        id = this.id.value,
        name = this.name.value,
        type = this.type.value,
        schoolDetails = this.schoolDetails.domainToEntity(),
        createdBy = this.createdBy.domainToEntity(),
        createdAt = this.createdAt,
        trainingTime = this.trainingTime,
        trainingLevelEntity = this.trainingLevel.toEntity(),
    )

fun Set<TrainingDetails>.setToDto(): Set<TrainingDto> =
    map { it.toDto() }.toSet()

fun TrainingDetails.toDto(trainingStatus: Boolean? = null, participationDto: List<TrainingParticipationDto>? = null , trainingCalendarId: Long? = null): TrainingDto =
    TrainingDto(
        id = this.id.value,
        name = this.name.value,
        type = this.type.value,
        schoolDetails = this.schoolDetails.domainToDto(),
        createdBy = this.createdBy.domainToDto(),
        createdAt = this.createdAt.toString(),
        trainingTime = this.trainingTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
        trainingStatus = trainingStatus,
        trainingLevel = this.trainingLevel.toDto(),
        participation = participationDto,
        trainingCalendarId = trainingCalendarId
    )
