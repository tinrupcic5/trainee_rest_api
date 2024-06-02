package com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.notification.common.interfaces.Notification
import com.bitroot.trainee.restapi.domain.notification.common.interfaces.NotificationId
import com.bitroot.trainee.restapi.domain.notification.common.interfaces.NotificationMessageEn
import com.bitroot.trainee.restapi.domain.notification.common.interfaces.NotificationMessageHr
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.SchoolDetailsEntity
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.UserEntity
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.toDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "notification")
data class NotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_details_id", referencedColumnName = "id")
    val schoolDetails: SchoolDetailsEntity,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: UserEntity,

    @Column(name = "message_en", nullable = false)
    val messageEn: String,

    @Column(name = "message_hr", nullable = false)
    val messageHr: String,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

)

fun NotificationEntity.toDomain(): Notification =
    Notification(
        id = NotificationId(this.id),
        schoolDetails = this.schoolDetails.toDomain(),
        user = this.user.toDomain(),
        messageEn = NotificationMessageEn(this.messageEn),
        messageHr = NotificationMessageHr(this.messageHr),
        createdAt = this.createdAt,
    )
