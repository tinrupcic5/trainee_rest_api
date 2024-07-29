package com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface SectionEntityJpaRepository : JpaRepository<SectionEntity, Long> {
    @Query(
        """
    SELECT * FROM section WHERE name = :name AND id = :sectionId
    """,
        nativeQuery = true,
    )
    fun getSectionByName(
        name: String,
        sectionId: Long,
    ): SectionEntity

    @Query(
        """
    SELECT * FROM section WHERE id = :sectionId
    """,
        nativeQuery = true,
    )
    fun getSectionById(sectionId: Long): SectionEntity?

    @Query(
        """
SELECT s.*
FROM section s
         INNER JOIN user_details ud ON s.user_details_id = ud.id
         INNER JOIN school_details sd ON ud.school_details_id = sd.id
WHERE sd.school_id = (
    SELECT sd2.school_id
    FROM user_details ud2
          INNER JOIN school_details sd2 ON ud2.school_details_id = sd2.id
    WHERE ud2.user_id = :userId
    );
       """,
        nativeQuery = true,
    )
    fun getAllSections(userId: Long): Set<SectionEntity>
}
