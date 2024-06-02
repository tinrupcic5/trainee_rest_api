package com.bitroot.trainee.restapi.domain.role

import com.bitroot.trainee.restapi.domain.role.adapter.outgoing.jpa.RoleRepository
import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class RoleServiceImpl(
    val roleRepository: RoleRepository,

) : RoleService {
    override fun findRoleByName(roleName: String): Role? =
        roleRepository.findRoleByName(roleName)

    override fun findRoleById(roleId: Long): Role =
        roleRepository.findRoleById(roleId)

    override fun getAllRoles(): Role =
        roleRepository.getAllRoles()

    override fun save(role: Role): String =
        roleRepository.save(role)
}
