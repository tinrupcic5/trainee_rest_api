package com.bitroot.trainee.restapi.domain.role.common.interfaces

import com.bitroot.trainee.restapi.domain.role.adapter.outgoing.jpa.RoleEntity

data class Role(
    val id: RoleId,
    val name: RoleName,
)

fun Role.domainToEntity(): RoleEntity =
    RoleEntity(
        id = this.id.value,
        roleName = this.name.value,
    )
