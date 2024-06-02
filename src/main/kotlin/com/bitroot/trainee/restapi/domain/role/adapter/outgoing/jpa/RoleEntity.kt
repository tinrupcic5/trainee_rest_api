package com.bitroot.trainee.restapi.domain.role.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.role.adapter.outgoing.web.RoleDto
import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleId
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleName
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "roles")
data class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "role_name", nullable = false)
    val roleName: String,
)

fun RoleEntity.toDto(): RoleDto =
    RoleDto(
        id = this.id,
        name = this.roleName,
    )

fun RoleEntity.toDomain(): Role =
    Role(
        id = RoleId(this.id),
        name = RoleName(this.roleName),
    )
