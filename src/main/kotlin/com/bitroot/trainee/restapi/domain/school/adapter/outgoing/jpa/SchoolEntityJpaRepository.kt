package com.bitroot.trainee.restapi.domain.school.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface SchoolEntityJpaRepository : JpaRepository<SchoolEntity, Long> {

    @Query(
        """
        SELECT * FROM school
    """,
        nativeQuery = true,
    )
    fun getAllSchool(): SchoolEntity?
}
