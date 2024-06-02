package com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.jpa.TrainingDetailsEntity
import com.bitroot.trainee.restapi.domain.training.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendar
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingCalendarId
import com.bitroot.trainee.restapi.domain.training.details.calendar.common.interfaces.TrainingStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.sql.Date

@Entity
@Table(name = "training_calendar")
data class TrainingCalendarEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val trainingDate: Date,

    @ManyToOne
    @JoinColumn(name = "training_details_id")
    val trainingDetails: TrainingDetailsEntity,

    @Column(nullable = false)
    val trainingStatus: Boolean, // canceled / active
)

fun TrainingCalendarEntity.toDomain(): TrainingCalendar =
    TrainingCalendar(
        id = TrainingCalendarId(this.id),
        trainingDate = this.trainingDate,
        trainingDetails = this.trainingDetails.toDomain(),
        trainingStatus = TrainingStatus(this.trainingStatus),
    )
