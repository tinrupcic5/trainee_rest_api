package com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.details.participation.common.interfaces.TrainingParticipation
import java.util.*

interface TrainingParticipationRepository {

    fun getAllParticipationForToday(): Set<TrainingParticipation>
    fun getParticipationById(trainingParticipationId: Long): TrainingParticipation
    fun getParticipationForUSerDetailsId(userDetailsId: Long): List<TrainingParticipation>
    fun save(trainingParticipation: TrainingParticipation): String
    fun getParticipantsForTheDate(trainingDetailsId: Long, date: Date, schoolDetailsId: Long): List<TrainingParticipation>
    fun findByTrainingCalendarIdAndUserDetailsId(trainingCalendarId: Long, userDetailsId: Long): TrainingParticipation?
    fun delete(trainingParticipationList: List<Long>): String
}
