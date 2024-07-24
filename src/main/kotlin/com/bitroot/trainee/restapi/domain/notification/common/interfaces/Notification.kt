package com.bitroot.trainee.restapi.domain.notification.common.interfaces

import com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.jpa.NotificationEntity
import com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.web.NotificationDto
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.domainToEntity
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.common.interfaces.domainToEntity
import java.time.LocalDateTime

data class Notification(
    val id: NotificationId,
    val schoolDetails: SchoolDetails,
    val user: User,
    val message: NotificationMessage,
    val createdAt: LocalDateTime,
)
fun Notification.toDto(): NotificationDto =
    NotificationDto(
        id = this.id.value!!,
        schoolDetailsDto = this.schoolDetails.domainToDto(),
        userDto = this.user.domainToDto(),
        message = this.message.value,
        createdAt = this.createdAt.toString(),
    )
fun Notification.toEntity(): NotificationEntity =
    NotificationEntity(
        id = this.id.value,
        schoolDetails = this.schoolDetails.domainToEntity(),
        user = this.user.domainToEntity(),
        message = this.message.value,
        createdAt = this.createdAt,
    )
