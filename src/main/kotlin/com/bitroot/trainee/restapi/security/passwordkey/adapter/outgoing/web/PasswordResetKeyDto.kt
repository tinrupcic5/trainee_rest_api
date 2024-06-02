package com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.web

import com.bitroot.trainee.restapi.appsettings.toLocalDateTime
import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleId
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleName
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web.UserDto
import com.bitroot.trainee.restapi.domain.user.common.interfaces.Password
import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserEnabled
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserId
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserName
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.PasswordResetKey
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.PasswordResetKeyId
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.ResetKey
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.Used

data class PasswordResetKeyDto(
    val id: Long? = null,
    val user: UserDto,
    val resetKey: String,
    val createdAt: String,
    val expiration: String,
    val used: Boolean,
)

fun PasswordResetKeyDto.toDomain(): PasswordResetKey =
    PasswordResetKey(
        id = PasswordResetKeyId(this.id),
        user = User(
            id = UserId(this.user.id),
            userName = UserName(this.user.userName),
            password = Password(this.user.password),
            createdAt = this.user.createdAt.toLocalDateTime(),
            lastTimeOnline = this.user.lastTimeOnline.toLocalDateTime(),
            role = Role(
                id = RoleId(this.user.role.id),
                name = RoleName(this.user.role.name),
            ),
            isEnabled = UserEnabled(this.user.isEnabled),
        ),
        resetKey = ResetKey(this.resetKey),
        createdAt = this.createdAt.toLocalDateTime(),
        expiration = this.expiration.toLocalDateTime(),
        used = Used(this.used),
    )
