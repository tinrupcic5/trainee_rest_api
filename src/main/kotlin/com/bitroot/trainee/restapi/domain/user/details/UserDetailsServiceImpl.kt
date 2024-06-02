package com.bitroot.trainee.restapi.domain.user.details

import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa.TrainingLevelRepository
import com.bitroot.trainee.restapi.domain.training.details.participation.TrainingParticipationService
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.UserRepository
import com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web.UserDetailsRequest
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class UserDetailsServiceImpl(
    private val userDetailsRepository: UserDetailsRepository,
    private val userRepository: UserRepository,
    private val trainingLevelRepository: TrainingLevelRepository,
    private val participationService: TrainingParticipationService,

) : UserDetailsService {
    private val logger = KotlinLogging.logger { }
    override fun saveUserDetails(userDetailsRequest: UserDetailsRequest): String {
        val userDetails = userDetailsRepository.getUserDetailsByUserId(userDetailsRequest.id)
        val trainingLevel = trainingLevelRepository.getTrainingLevelById(userDetailsRequest.trainingLevelId)

        val userCopy = userDetails.copy(
            trainingLevel = trainingLevel,
        )
        userDetailsRepository.saveUserDetails(userCopy)

        if (userDetails.trainingLevel?.id?.value != userDetailsRequest.trainingLevelId) {
            participationService.updateParticipationForUserDetailsId(userCopy)
        }

        return "UserDetails for ${userDetails.name.value} ${userDetails.lastName.value} updated."
    }
}
