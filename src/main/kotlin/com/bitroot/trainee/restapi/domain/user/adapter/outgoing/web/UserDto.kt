package com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web

import com.bitroot.trainee.restapi.appsettings.toLocalDateTime
import com.bitroot.trainee.restapi.domain.role.adapter.outgoing.web.RoleDto
import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleId
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleName
import com.bitroot.trainee.restapi.domain.user.common.interfaces.Password
import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserEnabled
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserId
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserName
import com.fasterxml.jackson.annotation.JsonIgnore

data class UserDto(
    val id: Long?,
    val userName: String,
    @JsonIgnore
    val password: String,
    val createdAt: String,
    val lastTimeOnline: String,
    val role: RoleDto,
    val isEnabled: Boolean,
)

fun UserDto.toDomain(): User =
    User(
        id = UserId(this.id),
        userName = UserName(this.userName),
        password = Password(this.password),
        createdAt = this.createdAt.toLocalDateTime(),
        lastTimeOnline = this.lastTimeOnline.toLocalDateTime(),
        role = Role(
            id = RoleId(this.role.id),
            name = RoleName(this.role.name),
        ),
        isEnabled = UserEnabled(this.isEnabled),
    )
