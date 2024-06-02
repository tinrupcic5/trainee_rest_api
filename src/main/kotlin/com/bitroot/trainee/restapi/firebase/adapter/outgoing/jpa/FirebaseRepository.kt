package com.bitroot.trainee.restapi.firebase.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.firebase.common.interfaces.FirebaseUser
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Component
interface FirebaseRepository {

    fun findFirebaseTokenByUserId(userId: Long): Optional<FirebaseUser>
    fun findFirebaseToken(token: String): Optional<FirebaseUser>
    fun save(firebaseUser: FirebaseUser): String
}
