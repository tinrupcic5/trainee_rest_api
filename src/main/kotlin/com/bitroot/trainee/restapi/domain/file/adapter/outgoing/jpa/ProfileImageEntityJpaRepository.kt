package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface ProfileImageEntityJpaRepository : JpaRepository<ProfileImageEntity, Long> {
    @Query(
        """
    SELECT * FROM profile_image WHERE school_id = :schoolId
    """,
        nativeQuery = true,
    )
    fun getProfileImageBySchoolId(schoolId: Long): ProfileImageEntity

    @Query(
        """
    SELECT * FROM profile_image WHERE name = :name
    """,
        nativeQuery = true,
    )
    fun getProfileImageByName(name: String): ProfileImageEntity
}
