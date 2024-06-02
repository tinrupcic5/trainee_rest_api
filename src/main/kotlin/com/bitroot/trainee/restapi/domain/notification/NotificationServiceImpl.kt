package com.bitroot.trainee.restapi.domain.notification

import com.bitroot.trainee.restapi.domain.notification.adapter.incoming.web.NotificationRequest
import com.bitroot.trainee.restapi.domain.notification.adapter.incoming.web.toDomain
import com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.jpa.NotificationRepository
import com.bitroot.trainee.restapi.domain.notification.common.interfaces.Notification
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.SchoolDetailsRepository
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.UserRepository
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class NotificationServiceImpl(
    val notificationRepository: NotificationRepository,
    val schoolDetailsRepository: SchoolDetailsRepository,
    val userRepository: UserRepository,
) : NotificationService {
    override fun getNotificationForSchoolDetailsId(schoolDetailsId: Long): List<Notification> =
        notificationRepository.getNotificationForSchoolDetailsId(schoolDetailsId)

    override fun getNotificationById(notificationId: Long): Notification =
        notificationRepository.getNotificationById(notificationId)

    override fun saveOrUpdateNotification(notification: NotificationRequest): String {
        val schoolDetailsId = schoolDetailsRepository.getSchoolDetailsById(notification.schoolDetailsId)
        val user = userRepository.getUserById(notification.createdByUser)
        return notificationRepository.saveOrUpdateNotification(
            notification.toDomain(
                schoolDetails = schoolDetailsId,
                user = user,
            ),
        )
    }

    override fun deleteNotification(notificationId: Long): String =
        notificationRepository.deleteNotification(notificationId)
}
