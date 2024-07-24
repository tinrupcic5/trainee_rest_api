package com.bitroot.trainee.restapi.domain.notification.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.notification.common.interfaces.Notification
import com.bitroot.trainee.restapi.domain.notification.common.interfaces.NotificationId
import com.bitroot.trainee.restapi.domain.notification.common.interfaces.NotificationMessage
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import java.time.LocalDateTime

data class NotificationRequest(
    val id: Long? = null,
    val schoolDetailsId: Long,
    val createdByUser: Long,
    val message: String,
)
fun NotificationRequest.toDomain(schoolDetails: SchoolDetails, user: User): Notification =
    Notification(
        id = NotificationId(this.id),
        schoolDetails = schoolDetails,
        user = user,
        message = NotificationMessage(this.message),
        createdAt = LocalDateTime.now(),
    )
