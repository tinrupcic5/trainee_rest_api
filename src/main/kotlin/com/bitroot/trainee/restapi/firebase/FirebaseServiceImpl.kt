package com.bitroot.trainee.restapi.firebase

import com.bitroot.trainee.restapi.domain.user.UserService
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web.toDomain
import com.bitroot.trainee.restapi.firebase.adapter.incoming.web.FirebaseUserRequest
import com.bitroot.trainee.restapi.firebase.adapter.incoming.web.requestToDomain
import com.bitroot.trainee.restapi.firebase.adapter.outgoing.jpa.FirebaseRepository
import com.bitroot.trainee.restapi.firebase.adapter.outgoing.jpa.domainToDto
import com.bitroot.trainee.restapi.firebase.adapter.outgoing.web.FirebaseTokenDto
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.*

@Service
@Validated
class FirebaseServiceImpl(
    val firebaseRepository: FirebaseRepository,
    val userService: UserService,
) : FirebaseService {
    private val logger = KotlinLogging.logger { }

    override fun findFirebaseTokenByUserId(userId: Long): Optional<FirebaseTokenDto> =
        firebaseRepository.findFirebaseTokenByUserId(userId).map { it.domainToDto() }

    override fun save(firebaseUserRequest: FirebaseUserRequest): String {
        val user = userService.getUserById(firebaseUserRequest.userId)
        return firebaseRepository.save(firebaseUserRequest.requestToDomain(user.toDomain()))
    }

    override fun checkAndUpdate(firebaseUserRequest: FirebaseUserRequest): String? {
        val user = userService.getUserById(firebaseUserRequest.userId)
        val firebaseUserByToken = firebaseRepository.findFirebaseToken(firebaseUserRequest.firebaseToken)

        return if (firebaseUserByToken.isPresent &&
            firebaseUserByToken.get().firebaseToken == firebaseUserRequest.firebaseToken &&
            firebaseUserByToken.get().user.id?.value == firebaseUserRequest.userId
        ) {
            logger.info("checkAndUpdate: firebase user exists.")
            firebaseUserByToken.get().firebaseToken
        } else {
            if (firebaseUserByToken.isPresent) {
                logger.info("checkAndUpdate: firebase user updated.")
                firebaseRepository.save(firebaseUserByToken.get().copy(user = user.toDomain()))
            } else {
                logger.info("checkAndUpdate: firebase user saved.")
                firebaseRepository.save(firebaseUserRequest.requestToDomain(user.toDomain()))
            }
        }
    }
}
