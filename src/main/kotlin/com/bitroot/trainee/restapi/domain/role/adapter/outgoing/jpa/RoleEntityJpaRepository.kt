package com.bitroot.trainee.restapi.domain.role.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface RoleEntityJpaRepository : JpaRepository<RoleEntity, Long> {

    @Query(
        """
        SELECT * FROM roles WHERE role_name =:roleName
    """,
        nativeQuery = true,
    )
    fun findRoleByName(roleName: String): RoleEntity?

    @Query(
        """
        SELECT * FROM roles WHERE id =:roleId
    """,
        nativeQuery = true,
    )
    fun findRoleById(roleId: Long): RoleEntity

    @Query(
        """
        SELECT * FROM roles
    """,
        nativeQuery = true,
    )
    fun getAllRoles(): RoleEntity
}
