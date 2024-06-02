package com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface SchoolDetailsEntityJpaRepository : JpaRepository<SchoolDetailsEntity, Long> {

    @Query(
        """
        SELECT * FROM school_details
    """,
        nativeQuery = true,
    )
    fun getAllSchoolDetails(): SchoolDetailsEntity?

    @Query(
        """
        SELECT * FROM school_details WHERE id =:schoolDetailsId
    """,
        nativeQuery = true,
    )
    fun getSchoolDetailsById(schoolDetailsId: Long): SchoolDetailsEntity
}
