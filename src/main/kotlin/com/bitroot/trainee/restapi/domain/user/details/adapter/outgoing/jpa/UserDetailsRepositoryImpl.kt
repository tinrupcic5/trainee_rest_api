package com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToEntity
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class UserDetailsRepositoryImpl(
    private val userDetailsEntityJpaRepository: UserDetailsEntityJpaRepository,
) : UserDetailsRepository {
    private val logger = KotlinLogging.logger { }

    override fun getUserDetailsByUserId(userId: Long): UserDetails =
        userDetailsEntityJpaRepository.getUserDetailsByUserId(userId).entityToDomain()

    @Modifying
    override fun saveUserDetails(userDetails: UserDetails): String {
        userDetailsEntityJpaRepository.save(userDetails.domainToEntity())
        logger.debug { "User details saved for user: ${userDetails.name} ${userDetails.lastName}" }
        return "User details saved for user: ${userDetails.name} ${userDetails.lastName}"
    }

    override fun getUserDetailsByTrainingLevelClassification(t: TrainingCalendarRequest): List<UserDetails> =
        userDetailsEntityJpaRepository.getUserDetailsByTrainingLevelClassification(t.schoolDetailsId, t.listOfTrainingLevelClassification!!).map { it.entityToDomain() }
}
