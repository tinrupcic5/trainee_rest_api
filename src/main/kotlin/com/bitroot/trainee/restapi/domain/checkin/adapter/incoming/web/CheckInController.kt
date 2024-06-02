package com.bitroot.trainee.restapi.domain.checkin.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.checkin.CheckInService
import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/checkin")
class CheckInController(
    val checkInService: CheckInService,
) {
    @PostMapping
    fun checkIn(@RequestBody checkInRequest: CheckInRequest): ResponseEntity<MessageBody> {
        val checkIn = checkInService.save(checkInRequest)
        return if (checkIn == "QR Code does not exist.") {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(checkIn.toMessageBody())
        } else {
            ResponseEntity.status(HttpStatus.OK).body(checkIn.toMessageBody())
        }
    }
}
