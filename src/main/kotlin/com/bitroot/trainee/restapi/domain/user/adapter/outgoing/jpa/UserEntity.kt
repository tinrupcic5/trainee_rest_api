package com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.role.adapter.outgoing.jpa.RoleEntity
import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleId
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleName
import com.bitroot.trainee.restapi.domain.user.common.interfaces.Password
import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserEnabled
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserId
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserName
import com.fasterxml.jackson.annotation.JsonIgnore
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
import java.util.*
import javax.validation.constraints.Size

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_name", nullable = false)
    val userName: String,

    @Column(name = "password", nullable = false)
    @Size(min = 4, message = "Minimum 4 characters")
    @JsonIgnore
    val password: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "last_time_online")
    val lastTimeOnline: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    val role: RoleEntity,

    @Column(name = "is_enabled")
    val isEnabled: Boolean,
)

fun UserEntity.toDomain(): User =
    User(
        id = UserId(this.id),
        userName = UserName(this.userName),
        password = Password(this.password),
        createdAt = this.createdAt,
        lastTimeOnline = this.lastTimeOnline,
        role = Role(
            id = RoleId(this.role.id),
            name = RoleName(this.role.roleName),
        ),
        isEnabled = UserEnabled(this.isEnabled),
    )

fun Set<UserEntity>.toDomainSet(): Set<User> {
    return this.map { userEntity ->
        User(
            id = UserId(userEntity.id),
            userName = UserName(userEntity.userName),
            password = Password(userEntity.password),
            createdAt = userEntity.createdAt,
            lastTimeOnline = userEntity.lastTimeOnline,
            role = Role(
                id = RoleId(userEntity.role.id),
                name = RoleName(userEntity.role.roleName),
            ),
            isEnabled = UserEnabled(userEntity.isEnabled),
        )
    }.toSet()
}

fun Optional<UserEntity>.optionalToOptionalDomain(): Optional<User> {
    return this.map { userEntity ->
        User(
            id = UserId(userEntity.id),
            userName = UserName(userEntity.userName),
            password = Password(userEntity.password),
            createdAt = userEntity.createdAt,
            lastTimeOnline = userEntity.lastTimeOnline,
            role = Role(
                id = RoleId(userEntity.role.id),
                name = RoleName(userEntity.role.roleName),
            ),
            isEnabled = UserEnabled(userEntity.isEnabled),
        )
    }
}
