package com.bitroot.trainee.restapi.firebase.common.interfaces

class FirebaseNotificationMessage(
    val recipientToken: String,
    val title: String,
    val body: String,
    val data: Map<String, String>,
)
