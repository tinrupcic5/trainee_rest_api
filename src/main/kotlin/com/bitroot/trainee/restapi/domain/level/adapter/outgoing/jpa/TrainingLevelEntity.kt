package com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevel
import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevelClassificationName
import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevelId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "training_level_classification_system")
data class TrainingLevelEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "training_level_classification")
    val trainingLevelClassification: String?,
)

fun TrainingLevelEntity.toDomain(): TrainingLevel =
    TrainingLevel(
        id = TrainingLevelId(this.id),
        trainingLevelClassificationName = TrainingLevelClassificationName(this.trainingLevelClassification),
    )
