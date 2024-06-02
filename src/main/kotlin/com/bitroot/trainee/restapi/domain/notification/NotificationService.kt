package com.bitroot.trainee.restapi.domain.notification

import com.bitroot.trainee.restapi.domain.notification.adapter.incoming.web.NotificationRequest
import com.bitroot.trainee.restapi.domain.notification.common.interfaces.Notification

interface NotificationService {
    fun getNotificationForSchoolDetailsId(schoolDetailsId: Long): List<Notification>
    fun getNotificationById(notificationId: Long): Notification
    fun saveOrUpdateNotification(notification: NotificationRequest): String
    fun deleteNotification(notificationId: Long): String
}
