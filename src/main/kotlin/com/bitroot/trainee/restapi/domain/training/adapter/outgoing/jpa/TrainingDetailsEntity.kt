package com.bitroot.trainee.restapi.domain.training.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa.TrainingLevelEntity
import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.SchoolDetailsEntity
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingDetails
import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingId
import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingName
import com.bitroot.trainee.restapi.domain.training.common.interfaces.TrainingType
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsEntity
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.entityToDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.sql.Time
import java.time.LocalDateTime

@Entity
@Table(name = "training_details")
data class TrainingDetailsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val type: String,

    @ManyToOne
    @JoinColumn(name = "school_details_id")
    val schoolDetails: SchoolDetailsEntity,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_level_classification_system_id", referencedColumnName = "id")
    val trainingLevelEntity: TrainingLevelEntity,

    @ManyToOne
    @JoinColumn(name = "created_by")
    val createdBy: UserDetailsEntity,

    @Column(nullable = false)
    val createdAt: LocalDateTime,

    @Column(nullable = false)
    val trainingTime: Time,

)

fun Set<TrainingDetailsEntity>.setToDomain(): Set<TrainingDetails> =
    map { it.toDomain() }.toSet()

fun TrainingDetailsEntity.toDomain(): TrainingDetails =
    TrainingDetails(
        id = TrainingId(this.id),
        name = TrainingName(this.name),
        type = TrainingType(this.type),
        schoolDetails = this.schoolDetails.toDomain(),
        createdBy = this.createdBy.entityToDomain(),
        createdAt = this.createdAt,
        trainingTime = this.trainingTime,
        trainingLevel = this.trainingLevelEntity.toDomain(),
    )
