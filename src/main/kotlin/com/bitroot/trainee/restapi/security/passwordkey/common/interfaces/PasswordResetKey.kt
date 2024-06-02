package com.bitroot.trainee.restapi.security.passwordkey.common.interfaces

import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.common.interfaces.domainToEntity
import com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.jpa.PasswordResetKeyEntity
import com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.web.PasswordResetKeyDto
import com.bitroot.trainee.restapi.security.passwordkey.localTimeToStringForMail
import java.time.LocalDateTime

data class PasswordResetKey(
    val id: PasswordResetKeyId,
    val user: User,
    val resetKey: ResetKey,
    val createdAt: LocalDateTime,
    val expiration: LocalDateTime,
    val used: Used,
)

fun PasswordResetKey.toEntity(): PasswordResetKeyEntity =
    PasswordResetKeyEntity(
        id = this.id.value,
        user = this.user.domainToEntity(),
        resetKey = this.resetKey.value,
        createdAt = this.createdAt,
        expiration = this.expiration,
        isUsed = this.used.value,
    )

fun PasswordResetKey.toDto(): PasswordResetKeyDto =
    PasswordResetKeyDto(
        id = this.id.value,
        user = this.user.domainToDto(),
        resetKey = this.resetKey.value,
        createdAt = this.createdAt.toString(),
        expiration = this.expiration.localTimeToStringForMail(),
        used = this.used.value,
    )
