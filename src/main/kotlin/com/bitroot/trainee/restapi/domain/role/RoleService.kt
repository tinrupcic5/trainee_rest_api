package com.bitroot.trainee.restapi.domain.role

import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role

interface RoleService {

    fun findRoleByName(roleName: String): Role?
    fun findRoleById(roleId: Long): Role

    fun getAllRoles(): Role

    fun save(role: Role): String
}
