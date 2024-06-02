package com.bitroot.trainee.restapi.firebase.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.user.MessageBody
import com.bitroot.trainee.restapi.firebase.FirebaseMessagingService
import com.bitroot.trainee.restapi.firebase.FirebaseService
import com.bitroot.trainee.restapi.firebase.common.interfaces.FirebaseNotificationMessage
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/firebase")
class FirebaseController(
    val firebaseMessagingService: FirebaseMessagingService,
    val firebaseService: FirebaseService,
) {

    @PostMapping("/notification")
    fun sendNotificationByToken(@RequestBody firebaseNotificationMessage: FirebaseNotificationMessage): String =
        firebaseMessagingService.sendNotificationByToken(firebaseNotificationMessage)

    @PostMapping("/user")
    fun saveFirebaseUser(@RequestBody firebaseUser: FirebaseUserRequest): ResponseEntity<MessageBody> =
        ResponseEntity.ok(firebaseService.save(firebaseUser).toMessageBody())

    @PutMapping("/user")
    fun checkAndUpdateFirebaseUser(@RequestBody firebaseUser: FirebaseUserRequest): ResponseEntity<MessageBody> =
        ResponseEntity.ok(firebaseService.checkAndUpdate(firebaseUser)?.toMessageBody())
}
