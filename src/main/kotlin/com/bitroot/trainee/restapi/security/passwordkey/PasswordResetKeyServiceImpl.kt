package com.bitroot.trainee.restapi.security.passwordkey

import com.bitroot.trainee.restapi.domain.membership.MembershipFeeService
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.UserRepository
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsRepository
import com.bitroot.trainee.restapi.email.EmailService
import com.bitroot.trainee.restapi.security.passwordkey.adapter.incoming.web.PasswordResetKeyRequest
import com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.jpa.PasswordResetKeyRepository
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.PasswordResetKey
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.PasswordResetKeyId
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.ResetKey
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.Used
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.toDto
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.PasswordResetKeyEmailResult
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
@Validated
class PasswordResetKeyServiceImpl(
    val passwordResetKeyRepository: PasswordResetKeyRepository,
    val userRepository: UserRepository,
    val userDetailsRepository: UserDetailsRepository,
    val emailService: EmailService,
    val membershipFeeService: MembershipFeeService,
) : PasswordResetKeyService {

    fun compareResetKey(newKey: String, oldKey: String): String {
        if (newKey == oldKey) {
            return UUID.randomUUID().toString().substring(0, 5)
        }
        return newKey
    }

    override fun createPasswordKey(passwordResetKeyRequest: PasswordResetKeyRequest): PasswordResetKeyEmailResult {
        // get user
        val user = userRepository.getUserById(passwordResetKeyRequest.userId)
        val userDetails = userDetailsRepository.getUserDetailsByUserId(passwordResetKeyRequest.userId)

        // get passwordResetKey if exists to upsert with new values
        val passwordResetKey = passwordResetKeyRepository.getPasswordKeyByUserId(passwordResetKeyRequest.userId)

        if (passwordResetKey != null) {
            val resetKey = UUID.randomUUID().toString().substring(0, 5)

            val passwordResetKeyDto = passwordResetKeyRepository.createPasswordKey(
                passwordResetKey.copy(
                    resetKey = ResetKey(compareResetKey(resetKey, passwordResetKey.resetKey.value)),
                    createdAt = LocalDateTime.now(),
                    expiration = LocalDateTime.now().plus(10, ChronoUnit.MINUTES),
                    used = Used(false),
                ),
            ).toDto()
            // populate request with existing passwordResetKey data
            return emailService.sendKeyPasswordEmail(
                passwordResetKeyRequest.email!!,
                userDetails,
                passwordResetKeyDto.resetKey,
                passwordResetKeyDto.expiration,

            )
        } else {
            // create random key
            val resetKey = UUID.randomUUID().toString().substring(0, 5)
            // create new passwordResetKey
            val passwordResetKey = PasswordResetKey(
                id = PasswordResetKeyId(passwordResetKeyRequest.id),
                user = user,
                resetKey = ResetKey(resetKey),
                createdAt = LocalDateTime.now(),
                expiration = LocalDateTime.now().plus(10, ChronoUnit.MINUTES),
                used = Used(false),
            )
            // create new createPasswordKey and send an email with createPasswordKey
            val passwordResetKeyDto = passwordResetKeyRepository.createPasswordKey(passwordResetKey)
            return emailService.sendKeyPasswordEmail(
                passwordResetKeyRequest.email!!,
                userDetails,
                resetKey,
                passwordResetKeyDto.expiration.localTimeToStringForMail(),
            )
        }
    }

    override fun updatePasswordResetKeyIsUsed(id: Long, isUsed: Boolean): Boolean =
        passwordResetKeyRepository.updatePasswordResetKeyIsUsed(id, isUsed)

    override fun checkIfKeyIsValidForUser(userId: Long, key: String): String =
        passwordResetKeyRepository.checkIfKeyIsValidForUser(userId, key)
}

fun LocalDateTime.localTimeToStringForMail(): String =
    this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy 'at' HH:mm:ss"))
fun LocalDateTime.localTimeToString(): String =
    this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
