package com.bitroot.trainee.restapi

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableScheduling
class TraineeRestApiApplication

@Value("\${firebase.file-name}")
private val fileName: String = ""

@Value("\${firebase.app-name}")
private val appName: String = ""

@Bean
fun fireBaseMessaging(): FirebaseMessaging {
    val googleCredentials: GoogleCredentials =
        GoogleCredentials.fromStream(
            ClassPathResource(fileName).inputStream,
        )
    val options = FirebaseOptions.builder()
        .setCredentials(googleCredentials)
        .build()
    val fire = FirebaseApp.initializeApp(options, appName)
    return FirebaseMessaging.getInstance(fire)
}
fun main(args: Array<String>) {
    runApplication<TraineeRestApiApplication>(*args)
}
