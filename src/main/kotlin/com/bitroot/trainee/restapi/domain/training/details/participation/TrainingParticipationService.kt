package com.bitroot.trainee.restapi.domain.training.details.participation

import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.TrainingParticipationDto
import com.bitroot.trainee.restapi.domain.training.details.participation.adapter.outgoing.web.TrainingParticipationRequest
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails

interface TrainingParticipationService {
    fun save(trainingParticipationRequest: TrainingParticipationRequest): String
    fun getParticipantsForTheDate(trainingDetailsId: Long, date: String, schoolDetailsId: Long): List<TrainingParticipationDto>
    fun updateParticipationForUserDetailsId(userDetails: UserDetails)
}
