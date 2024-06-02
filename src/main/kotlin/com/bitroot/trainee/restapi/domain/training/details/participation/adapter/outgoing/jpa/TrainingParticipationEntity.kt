package com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa.TrainingCalendarEntity
import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.AttendedStatus
import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.TrainingParticipation
import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.TrainingParticipationId
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsEntity
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.entityToDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "training_participation")
data class TrainingParticipationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "training_calendar_id")
    val trainingCalendar: TrainingCalendarEntity,

    @ManyToOne
    @JoinColumn(name = "user_details_id")
    val user: UserDetailsEntity,

    @Column(nullable = false)
    val attendedStatus: Boolean,
)

fun TrainingParticipationEntity.toDomain(): TrainingParticipation =
    TrainingParticipation(
        id = TrainingParticipationId(this.id),
        trainingCalendar = this.trainingCalendar.toDomain(),
        userDetails = this.user.entityToDomain(),
        attendedStatus = AttendedStatus(this.attendedStatus),
    )
