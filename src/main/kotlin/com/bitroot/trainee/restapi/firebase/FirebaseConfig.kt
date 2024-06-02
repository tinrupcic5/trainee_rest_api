package com.bitroot.trainee.restapi.firebase

import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FirebaseConfig {
    init {
        FirebaseApp.initializeApp()
    }

    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }
}
