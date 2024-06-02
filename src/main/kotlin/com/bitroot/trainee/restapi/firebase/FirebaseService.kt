package com.bitroot.trainee.restapi.firebase

import com.bitroot.trainee.restapi.firebase.adapter.incoming.web.FirebaseUserRequest
import com.bitroot.trainee.restapi.firebase.adapter.outgoing.web.FirebaseTokenDto
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.util.*

@Service
@Validated
interface FirebaseService {
    fun findFirebaseTokenByUserId(userId: Long): Optional<FirebaseTokenDto>
    fun save(firebaseUserRequest: FirebaseUserRequest): String
    fun checkAndUpdate(firebaseUserRequest: FirebaseUserRequest): String?
}
