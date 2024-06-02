package com.bitroot.trainee.restapi.domain.role.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role
import com.bitroot.trainee.restapi.domain.role.common.interfaces.domainToEntity
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class RoleRepositoryImpl(
    private val roleEntityJpaRepository: RoleEntityJpaRepository,
) : RoleRepository {
    private val logger = KotlinLogging.logger { }

    override fun findRoleByName(roleName: String): Role? =
        roleEntityJpaRepository.findRoleByName(roleName)?.toDomain()

    override fun findRoleById(roleId: Long): Role =
        roleEntityJpaRepository.findRoleById(roleId).toDomain()

    override fun getAllRoles(): Role =
        roleEntityJpaRepository.getAllRoles().toDomain()

    override fun save(role: Role): String {
        roleEntityJpaRepository.save(role.domainToEntity())
        logger.info { "Role with name: ${role.name} saved." }
        return "Role saved."
    }
}
