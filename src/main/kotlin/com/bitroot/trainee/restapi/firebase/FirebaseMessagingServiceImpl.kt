package com.bitroot.trainee.restapi.firebase

import com.bitroot.trainee.restapi.firebase.common.interfaces.FirebaseNotificationMessage
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class FirebaseMessagingServiceImpl(
    var firebaseMessaging: FirebaseMessaging,
) : FirebaseMessagingService {
    private val logger = KotlinLogging.logger { }

    override fun sendNotificationByToken(msg: FirebaseNotificationMessage): String {
        val notification = Notification
            .builder()
            .setTitle(msg.title)
            .setBody(msg.body)
            .build()
        val message = Message
            .builder()
            .setToken(msg.recipientToken)
            .setNotification(notification)
            .putAllData(msg.data)
            .build()
        try {
            firebaseMessaging.send(message)
            logger.info("SEND : " + msg.recipientToken)
            return "Success"
        } catch (e: Exception) {
            e.printStackTrace()
            logger.error("Error  ")
            return "Error"
        }
    }
}
