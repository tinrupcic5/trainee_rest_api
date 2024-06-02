package com.bitroot.trainee.restapi.domain.training.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface TrainingEntityJpaRepository : JpaRepository<TrainingDetailsEntity, Long> {

    @Query(
        """
    SELECT td.* 
    FROM training_details td
    INNER JOIN training_calendar tc ON td.id = tc.training_details_id
    WHERE tc.training_date = CURRENT_DATE
    """,
        nativeQuery = true,
    )
    fun getAllTrainingForToday(): Set<TrainingDetailsEntity>

    @Query(
        """
    SELECT td.* 
    FROM training_details td
    INNER JOIN training_calendar tc ON td.id = tc.training_details_id
    WHERE tc.training_date = :date
    AND td.school_details_id = :schoolDetailsId
    """,
        nativeQuery = true,
    )
    fun getAllTrainingForDate(schoolDetailsId: Long, date: Date): Optional<List<TrainingDetailsEntity>>

    @Query(
        """
    SELECT *
    FROM training_details 
    WHERE id IN :trainingDetailsId
    """,
        nativeQuery = true,
    )
    fun getListOfTrainingByTrainingDetailsId(trainingDetailsId: List<Long>): List<TrainingDetailsEntity>

    @Query(
        """
    SELECT *
    FROM training_details 
    WHERE training_level_classification_system_id = :trainingLevelId
    """,
        nativeQuery = true,
    )
    fun getTrainingByTrainingLevel(trainingLevelId: Long): List<TrainingDetailsEntity>
}
