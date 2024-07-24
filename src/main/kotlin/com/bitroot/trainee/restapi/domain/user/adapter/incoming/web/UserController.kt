package com.bitroot.trainee.restapi.domain.user.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.qrcode.QrCodeService
import com.bitroot.trainee.restapi.domain.role.common.interfaces.Roles
import com.bitroot.trainee.restapi.domain.user.MessageBody
import com.bitroot.trainee.restapi.domain.user.UserService
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web.UserLogin
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web.UserLoginTokenDto
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserId
import com.bitroot.trainee.restapi.security.jwt.JwtService
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    val userService: UserService,
    val jwtService: JwtService,
    val qrCodeService: QrCodeService,
) {
    private val logger = KotlinLogging.logger { }

    @PostMapping("/login")
    fun login(
        @RequestBody user: UserLogin,
    ): ResponseEntity<UserLoginTokenDto> {
        val usr = userService.login(user.userName, user.password)

        logger.info("${usr.get().name} ${usr.get().lastName} is trying to log in.")
        return if (usr.isPresent) {
            if (usr
                    .get()
                    .user.role.name
                    .uppercase() == Roles.SCANNER.name.uppercase()
            ) {
                ResponseEntity.ok(
                    UserLoginTokenDto(
                        usr.get(),
                        jwtService.create(usr.get().id),
                        refreshToken = jwtService.createRefreshToken(usr.get().id!!),
                    ),
                )
            } else {
                ResponseEntity.ok(
                    UserLoginTokenDto(
                        usr.get(),
                        jwtService.create(usr.get().id),
                        qrCodeService.getQrCodeForUser(UserId(usr.get().user.id!!)).qrCode!!.value,
                        refreshToken = jwtService.createRefreshToken(usr.get().id!!),
                    ),
                )
            }
        } else {
            logger.error(
                "UNAUTHORIZED: {}, is enabled: {}",
                HttpStatus.UNAUTHORIZED.toString(),
                usr.get().user.isEnabled,
            )
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @GetMapping("/logout")
    fun logout(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorizationHeader: String,
    ): ResponseEntity<MessageBody> {
        val token = jwtService.getJwtFromHeader(authorizationHeader)

        jwtService.isTokenInvalid(token)
        jwtService.invalidateToken(token)
        logger.info("Logout successful")
        return ResponseEntity.ok("Logout successful".toMessageBody())
    }

    @PutMapping("/enable/{userId}/{isEnabled}")
    fun setUserEnableStatus(
        @PathVariable userId: Long,
        @PathVariable isEnabled: Boolean,
    ): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK).body(userService.setUserEnableStatus(userId, isEnabled).toMessageBody())

    @DeleteMapping("/{userId}")
    fun deleteUser(
        @PathVariable userId: Long,
    ): ResponseEntity<MessageBody> = ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(userId).toMessageBody())

    @PutMapping("/password")
    fun changePassword(
        @RequestBody userChangePasswordRequest: UserChangePasswordRequest,
    ): ResponseEntity<MessageBody> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.changePassword(userChangePasswordRequest).toMessageBody())

    @PostMapping("/guest/{language}")
    fun saveGuest(
        @PathVariable language: String,
    ): ResponseEntity<UserLoginTokenDto> {
        val guestUser = userService.saveGuest(language)

        val usr = userService.login(guestUser.user.userName, guestUser.user.userName)

        return if (usr.isPresent) {
            ResponseEntity.ok(
                UserLoginTokenDto(
                    usr.get(),
                    jwtService.create(usr.get().id),
                ),
            )
        } else {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping
    fun registerUser(
        @RequestBody user: UserRegisterRequest,
    ): ResponseEntity<MessageBody> = ResponseEntity.status(HttpStatus.OK).body(MessageBody(userService.register(user)))

    @PutMapping
    fun updateUser(
        @RequestBody user: UserRegisterRequest,
    ): ResponseEntity<MessageBody> = ResponseEntity.status(HttpStatus.OK).body(MessageBody(userService.register(user)))

    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestBody refreshTokenRequest: RefreshTokenRequest,
    ): ResponseEntity<RefreshTokenResponse> {
        val userId = jwtService.validateRefreshToken(refreshTokenRequest.refreshToken)
        val newAccessToken = jwtService.create(userId)
        val newRefreshToken = jwtService.createRefreshToken(userId)

        return ResponseEntity.ok(
            RefreshTokenResponse(
                accessToken = newAccessToken,
                refreshToken = newRefreshToken,
            ),
        )
    }
}

data class RefreshTokenRequest(
    val refreshToken: String,
)

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
)
