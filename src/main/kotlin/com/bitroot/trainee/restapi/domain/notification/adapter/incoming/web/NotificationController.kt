package com.bitroot.trainee.restapi.domain.notification.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.notification.NotificationService
import com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.web.NotificationDto
import com.bitroot.trainee.restapi.domain.notification.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notification")
class NotificationController(
    val notificationService: NotificationService,
) {

    @GetMapping("/school/{schoolDetailsId}")
    fun getNotificationForSchoolDetailsId(@PathVariable schoolDetailsId: Long): ResponseEntity<List<NotificationDto>> =
        ResponseEntity.status(HttpStatus.OK).body(
            notificationService.getNotificationForSchoolDetailsId(schoolDetailsId).map { it.toDto() },
        )

    @DeleteMapping
    fun deleteNotification(@PathVariable notificationId: Long): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK).body(notificationService.deleteNotification(notificationId).toMessageBody())

    @GetMapping("/{notificationId}")
    fun getNotificationById(@PathVariable notificationId: Long): ResponseEntity<NotificationDto> =
        ResponseEntity.status(HttpStatus.OK).body(
            notificationService.getNotificationById(notificationId).toDto(),
        )

    @PostMapping
    fun saveOrUpdateNotification(@RequestBody notificationRequest: NotificationRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK).body(notificationService.saveOrUpdateNotification(notificationRequest).toMessageBody())
}
