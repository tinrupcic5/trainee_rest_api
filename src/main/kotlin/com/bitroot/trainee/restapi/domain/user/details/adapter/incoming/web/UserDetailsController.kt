package com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.user.MessageBody
import com.bitroot.trainee.restapi.domain.user.details.UserDetailsService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user-details")
class UserDetailsController(
    val userDetailsService: UserDetailsService,
) {
    private val logger = KotlinLogging.logger { }

    @PostMapping("/training-level")
    fun updateUser(@RequestBody userDetailsRequest: UserDetailsRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK).body(MessageBody(userDetailsService.saveUserDetails(userDetailsRequest)))
}
