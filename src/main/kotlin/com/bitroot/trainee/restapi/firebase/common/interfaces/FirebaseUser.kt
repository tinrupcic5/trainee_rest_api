package com.bitroot.trainee.restapi.firebase.common.interfaces

import com.bitroot.trainee.restapi.domain.user.common.interfaces.User

data class FirebaseUser(
    val firebaseId: Long? = null,
    val firebaseToken: String,
    val user: User,
)
