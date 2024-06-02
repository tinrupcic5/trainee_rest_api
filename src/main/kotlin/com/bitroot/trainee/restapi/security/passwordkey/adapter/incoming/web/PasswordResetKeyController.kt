package com.bitroot.trainee.restapi.security.passwordkey.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.user.MessageBody
import com.bitroot.trainee.restapi.security.passwordkey.PasswordResetKeyService
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.PasswordResetKeyEmailResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reset")
class PasswordResetKeyController(
    val passwordResetKeyService: PasswordResetKeyService,
) {

    @PostMapping
    fun sendMePasswordResetKey(@RequestBody passwordResetKeyRequest: PasswordResetKeyRequest): ResponseEntity<*> {
        return when (val result = passwordResetKeyService.createPasswordKey(passwordResetKeyRequest)) {
            is PasswordResetKeyEmailResult.Success -> ResponseEntity.status(HttpStatus.OK)
                .body(result.passwordResetKeyEmailDto)

            is PasswordResetKeyEmailResult.Failed -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result.exception)
        }
    }

    @GetMapping
    fun checkIfKeyIsValidForUser(@RequestBody passwordResetKeyRequest: PasswordResetKeyRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK).body(
            passwordResetKeyService.checkIfKeyIsValidForUser(
                passwordResetKeyRequest.userId,
                passwordResetKeyRequest.resetKey!!,
            ).toMessageBody(),
        )
}
