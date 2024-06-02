package com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface TrainingLevelEntityJpaRepository : JpaRepository<TrainingLevelEntity, Long> {

    @Query(
        value = """
        SELECT * FROM training_level_classification_system
        WHERE training_level_classification = :trainingLevel
    """,
        nativeQuery = true,
    )
    fun getTrainingLevelByName(trainingLevel: String): TrainingLevelEntity
}
