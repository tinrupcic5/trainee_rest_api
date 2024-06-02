package com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.TrainingParticipation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface TrainingParticipationEntityJpaRepository : JpaRepository<TrainingParticipationEntity, Long> {

    @Query(
        """
    SELECT * 
    FROM training_participation
    """,
        nativeQuery = true,
    )
    fun getAllParticipationForToday(): Set<TrainingParticipationEntity>

    @Query(
        """
        INSERT INTO training_participation (training_calendar_id, user_details_id, attended_status)
        VALUES (:trainingCalendarId, :userDetailsId, :attendedStatus)
        ON CONFLICT (training_calendar_id, user_details_id) DO UPDATE
        SET attended_status = :attendedStatus
        RETURNING training_calendar_id;
    """,
        nativeQuery = true,
    )
    fun saveParticipants(trainingCalendarId: Long, userDetailsId: Long, attendedStatus: Boolean): Int

    @Query(
        """
          SELECT * FROM training_participation
                WHERE training_calendar_id IN (
                    SELECT id FROM training_calendar
                    WHERE training_date = :date
                    AND training_details_id IN (
                        SELECT id FROM training_details
                        WHERE school_details_id = :schoolDetailsId
                        AND id = :trainingDetailsId
                    )
                )
    """,
        nativeQuery = true,
    )
    fun getParticipantsForTheDate(
        trainingDetailsId: Long,
        date: Date,
        schoolDetailsId: Long,
    ): List<TrainingParticipationEntity>

    @Query(
        """
          SELECT * FROM training_participation
                WHERE user_details_id = :userDetailsId
    """,
        nativeQuery = true,
    )
    fun getParticipationForUSerDetailsId(userDetailsId: Long): List<TrainingParticipationEntity>

    @Query(
        """
          SELECT * FROM training_participation
                WHERE training_calendar_id = :trainingCalendarId
                AND user_details_id = :userDetailsId
    """,
        nativeQuery = true,
    )
    fun findByTrainingCalendarIdAndUserDetailsId(trainingCalendarId: Long, userDetailsId: Long): TrainingParticipation?
}
