package com.bitroot.trainee.restapi.domain.user.common.interfaces

import com.bitroot.trainee.restapi.domain.role.adapter.outgoing.jpa.RoleEntity
import com.bitroot.trainee.restapi.domain.role.adapter.outgoing.web.RoleDto
import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role
import com.bitroot.trainee.restapi.domain.role.common.interfaces.domainToEntity
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.UserEntity
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web.UserDto
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import java.util.Optional

data class User(
    val id: UserId? = null,
    val userName: UserName,
    @JsonIgnore
    val password: Password,
    val createdAt: LocalDateTime,
    val lastTimeOnline: LocalDateTime,
    val role: Role,
    val isEnabled: UserEnabled,
)

fun User.domainToDto(): UserDto =
    UserDto(
        id = this.id?.value,
        userName = this.userName.value,
        createdAt = this.createdAt.toString(),
        lastTimeOnline = this.lastTimeOnline.toString(),
        password = this.password.value,
        role = RoleDto(
            this.role.id.value,
            this.role.name.value,
        ),
        isEnabled = this.isEnabled.value,
    )

fun User.domainToEntity(): UserEntity =
    UserEntity(
        id = this.id?.value,
        userName = this.userName.value,
        password = this.password.value,
        createdAt = this.createdAt,
        lastTimeOnline = this.lastTimeOnline,
        role = RoleEntity(
            this.role.id.value,
            this.role.name.value,
        ),
        isEnabled = this.isEnabled.value,
    )

fun Optional<User>.optionalDomainToEntity(): UserEntity {
    return this.map { user ->
        UserEntity(
            id = user.id?.value,
            userName = user.userName.value,
            password = user.password.value,
            createdAt = user.createdAt,
            lastTimeOnline = user.lastTimeOnline,
            role = user.role.domainToEntity(),
            isEnabled = user.isEnabled.value,
        )
    }.orElseGet {
        UserEntity(
            id = null,
            userName = "",
            password = "",
            createdAt = LocalDateTime.now(),
            lastTimeOnline = LocalDateTime.now(),
            role = RoleEntity(id = null, roleName = ""),
            isEnabled = true,
        )
    }
}
