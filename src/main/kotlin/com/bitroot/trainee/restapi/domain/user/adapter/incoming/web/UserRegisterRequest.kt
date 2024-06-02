package com.bitroot.trainee.restapi.domain.user.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleId
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleName
import com.bitroot.trainee.restapi.domain.user.common.interfaces.Password
import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserEnabled
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserId
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserName
import com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web.UserDetailsRegisterRequest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime

data class UserRegisterRequest(
    val userName: String,
    val password: String? = null,
    val role: String,
    val userDetails: UserDetailsRegisterRequest,
    val language: String,
)

fun UserRegisterRequest.requestToDomain(passwordEncoder: BCryptPasswordEncoder): User =
    User(
        id = UserId(null),
        userName = UserName(this.userName),
        password = Password(passwordEncoder.encode(this.password)),
        createdAt = LocalDateTime.now(),
        lastTimeOnline = LocalDateTime.now(),
        role = Role(RoleId(null), RoleName(this.role)),
        isEnabled = UserEnabled(true), // default user is enabled
    )
