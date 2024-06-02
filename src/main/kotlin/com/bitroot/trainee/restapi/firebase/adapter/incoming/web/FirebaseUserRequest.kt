package com.bitroot.trainee.restapi.firebase.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.firebase.common.interfaces.FirebaseUser

data class FirebaseUserRequest(
    val firebaseToken: String,
    val userId: Long,
)

fun FirebaseUserRequest.requestToDomain(user: User) =
    FirebaseUser(
        firebaseId = null,
        firebaseToken = this.firebaseToken,
        user = user,
    )
