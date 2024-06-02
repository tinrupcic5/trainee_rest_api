package com.bitroot.trainee.restapi.domain.role.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role

interface RoleRepository {

    fun findRoleByName(roleName: String): Role?

    fun findRoleById(roleId: Long): Role
    fun getAllRoles(): Role
    fun save(role: Role): String
}
