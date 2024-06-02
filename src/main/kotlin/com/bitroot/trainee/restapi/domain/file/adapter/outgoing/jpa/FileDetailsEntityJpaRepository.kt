package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface FileDetailsEntityJpaRepository : JpaRepository<FileDetailsEntity, Long> {
    @Query(
        """
    SELECT * FROM file_details WHERE id = :id
    """,
        nativeQuery = true,
    )
    fun getFileDetailsById(id: Long): FileDetailsEntity

    @Query(
        """
    SELECT * FROM file_details WHERE section_id IN :sectionIds
    """,
        nativeQuery = true,
    )
    fun getFileDetailsBySectionId(sectionIds: Set<Long>): List<FileDetailsEntity>

    @Query(
        """
    SELECT DISTINCT fd.*
FROM file_details fd
         JOIN section s ON fd.section_id = s.id
         JOIN user_details ud ON s.user_details_id = ud.id
         JOIN users u ON ud.user_id = u.id
         JOIN school_details sd ON ud.school_details_id = sd.id
WHERE u.id = :userId
  AND sd.school_Id = (
    SELECT school_Id
    FROM user_details
    WHERE user_id = :userId
);
    """,
        nativeQuery = true,
    )
    fun getAllFilesForSchool(userId: Long): Set<FileDetailsEntity>

    @Query(
        """
    SELECT * FROM file_details where name = :name
    """,
        nativeQuery = true,
    )
    fun getFileDetailsByName(name: String): FileDetailsEntity
}
