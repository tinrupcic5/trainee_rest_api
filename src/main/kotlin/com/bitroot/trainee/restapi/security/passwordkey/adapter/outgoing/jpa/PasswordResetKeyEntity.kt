package com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.UserEntity
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.PasswordResetKey
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.PasswordResetKeyId
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.ResetKey
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.Used
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "password_reset_keys")
data class PasswordResetKeyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: UserEntity,

    @Column(name = "reset_key", nullable = false)
    val resetKey: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "expiration", nullable = false)
    val expiration: LocalDateTime,

    @Column(name = "is_used", nullable = false)
    val isUsed: Boolean,
)

fun PasswordResetKeyEntity.toDomain(): PasswordResetKey =
    PasswordResetKey(
        id = PasswordResetKeyId(this.id),
        user = this.user.toDomain(),
        resetKey = ResetKey(this.resetKey),
        createdAt = this.createdAt,
        expiration = this.expiration,
        used = Used(this.isUsed),
    )
