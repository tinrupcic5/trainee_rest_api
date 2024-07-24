package com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.notification.common.interfaces.Notification
import com.bitroot.trainee.restapi.domain.notification.common.interfaces.toEntity
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.domainToEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class NotificationRepositoryImpl(
    val notificationEntityJpaRepository: NotificationEntityJpaRepository,
) : NotificationRepository {
    override fun getNotificationForSchoolDetailsId(schoolDetailsId: Long): List<Notification> =
        notificationEntityJpaRepository.getNotificationForSchoolDetailsId(schoolDetailsId).map { it.toDomain() }

    override fun getNotificationById(notificationId: Long): Notification =
        notificationEntityJpaRepository.getReferenceById(notificationId).toDomain()

    @Modifying
    override fun saveOrUpdateNotification(notificationRequest: Notification): String {
        if (notificationRequest.id.value != null) {
            val stack = notificationEntityJpaRepository.getReferenceById(notificationRequest.id.value)
            notificationEntityJpaRepository.save(
                stack.copy(
                    message = notificationRequest.message.value,
                    schoolDetails = notificationRequest.schoolDetails.domainToEntity(),
                ),
            )
            return "Notification updated."
        } else {
            notificationEntityJpaRepository.save(notificationRequest.toEntity())
            return "Notification is saved."
        }
    }

    override fun deleteNotification(notificationId: Long): String {
        val stack = notificationEntityJpaRepository.getReferenceById(notificationId)

        notificationEntityJpaRepository.delete(stack)
        return "Notification deleted."
    }
}
