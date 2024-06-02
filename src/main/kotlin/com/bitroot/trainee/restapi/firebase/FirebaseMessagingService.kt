package com.bitroot.trainee.restapi.firebase

import com.bitroot.trainee.restapi.firebase.common.interfaces.FirebaseNotificationMessage

interface FirebaseMessagingService {

    fun sendNotificationByToken(msg: FirebaseNotificationMessage): String
}
