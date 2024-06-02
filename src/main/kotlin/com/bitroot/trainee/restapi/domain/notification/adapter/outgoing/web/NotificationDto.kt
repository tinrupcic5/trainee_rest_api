package com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.web.SchoolDetailsDto
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web.UserDto

data class NotificationDto(
    val id: Long,
    val schoolDetailsDto: SchoolDetailsDto,
    val userDto: UserDto,
    val messageEn: String,
    val messageHr: String,
    val createdAt: String,
)
