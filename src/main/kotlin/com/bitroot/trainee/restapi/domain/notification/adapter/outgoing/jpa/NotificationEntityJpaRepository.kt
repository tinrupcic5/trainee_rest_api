package com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface NotificationEntityJpaRepository : JpaRepository<NotificationEntity, Long> {

    @Query(
        """
       SELECT *
        FROM notification
        WHERE school_details_id = :schoolDetailsId
        ORDER BY created_at DESC
    """,
        nativeQuery = true,
    )
    fun getNotificationForSchoolDetailsId(schoolDetailsId: Long): List<NotificationEntity>
}
//          AND (expiration IS NULL OR expiration > CURRENT_TIMESTAMP);
