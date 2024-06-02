package com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface TrainingCalendarEntityJpaRepository : JpaRepository<TrainingCalendarEntity, Long> {

    @Modifying
    @Query(
        value = """
        INSERT INTO training_calendar (training_date, training_details_id, training_status)
        SELECT 
            (training_calendar.training_date) AS training_date,
            :trainingDetailsId AS training_details_id,
            :trainingStatus AS training_status
        FROM generate_series(
            :startDate, 
            :endDate,
            INTERVAL '7 days'
        ) AS training_calendar(training_date)
             ON CONFLICT (training_date, training_details_id) DO UPDATE
        SET 
            training_details_id = :trainingDetailsId,
            training_status = :trainingStatus
    """,
        nativeQuery = true,
    )
    fun saveDatesUntilEndDateForEveryWeek(
        startDate: Date,
        endDate: Date,
        trainingDetailsId: Long,
        trainingStatus: Boolean,
    ): Int

    @Query(
        value = """
        SELECT * FROM training_calendar
        WHERE id = :trainingCalendarId
    """,
        nativeQuery = true,
    )
    fun getTrainingCalendarById(trainingCalendarId: Long): TrainingCalendarEntity

    @Query(
        value = """
        SELECT * FROM training_calendar
        WHERE training_details_id = :trainingDetailsId 
        AND training_date = :date
    """,
        nativeQuery = true,
    )
    fun getTrainingCalendarByTrainingDetailsIdAndDate(trainingDetailsId: Long, date: Date): List<TrainingCalendarEntity>

    @Query(
        value = """
        SELECT * 
        FROM training_calendar 
        WHERE training_details_id = :trainingDetailsId 
        AND training_date BETWEEN :from AND :to
    """,
        nativeQuery = true,
    )
    fun getTrainingCalendarByTrainingDetailsIdAndDateFromTo(trainingDetailsId: Long, from: Date, to: Date): List<TrainingCalendarEntity>

    @Query(
        value = """
        SELECT * 
        FROM training_calendar 
        WHERE id IN :trainingCalendarIdList
    """,
        nativeQuery = true,
    )
    fun getTrainingCalendarByListOfId(trainingCalendarIdList: List<Long>): List<TrainingCalendarEntity>

    @Query(
        value = """
        SELECT * 
        FROM training_calendar 
        WHERE training_details_id IN :trainingDetailsIdList
    """,
        nativeQuery = true,
    )
    fun getTrainingCalendarByListOfTrainingDetailsIds(trainingDetailsIdList: List<Long>): List<TrainingCalendarEntity>

    @Modifying
    @Query(
        value = """
        UPDATE training_calendar
        SET training_status = :trainingStatus
        WHERE id = :trainingCalendarId
    """,
        nativeQuery = true,
    )
    fun updateTraining(trainingCalendarId: Long, trainingStatus: Boolean)

    @Query(
        value = """
        SELECT * 
        FROM training_calendar 
        WHERE training_details_id = :trainingDetailsId 
        AND training_date = :trainingDate
    """,
        nativeQuery = true,
    )
    fun getTrainingCalendarByTrainingId(trainingDetailsId: Long, trainingDate: Date): TrainingCalendarEntity
}
