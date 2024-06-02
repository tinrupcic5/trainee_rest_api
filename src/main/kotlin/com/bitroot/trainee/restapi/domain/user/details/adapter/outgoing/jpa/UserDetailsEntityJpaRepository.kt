package com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface UserDetailsEntityJpaRepository : JpaRepository<UserDetailsEntity, Long> {

    @Query(
        """
        SELECT * FROM user_details WHERE user_id =:userId
    """,
        nativeQuery = true,
    )
    fun getUserDetailsByUserId(userId: Long): UserDetailsEntity

    @Query(
        """
    SELECT *
    FROM user_details ud 
    WHERE ud.school_details_id = :schoolDetailsId 
    AND ud.training_level_classification_system_id IN :listOfTrainingLevelClassification
    """,
        nativeQuery = true,
    )
    fun getUserDetailsByTrainingLevelClassification(
        schoolDetailsId: Long,
        listOfTrainingLevelClassification: List<Long>,
    ): List<UserDetailsEntity>
}
