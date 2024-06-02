package com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.notification.common.interfaces.Notification

interface NotificationRepository {

    fun getNotificationForSchoolDetailsId(schoolDetailsId: Long): List<Notification>
    fun getNotificationById(notificationId: Long): Notification
    fun saveOrUpdateNotification(notificationRequest: Notification): String
    fun deleteNotification(notificationId: Long): String
}
