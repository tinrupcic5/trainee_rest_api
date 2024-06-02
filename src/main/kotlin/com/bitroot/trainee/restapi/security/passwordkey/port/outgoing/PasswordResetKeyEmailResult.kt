package com.bitroot.trainee.restapi.security.passwordkey.port.outgoing

import com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.web.PasswordResetKeyEmailDto

sealed interface PasswordResetKeyEmailResult {
    data class Success(val passwordResetKeyEmailDto: PasswordResetKeyEmailDto) : PasswordResetKeyEmailResult
    data class Failed(val exception: Exception) : PasswordResetKeyEmailResult
}
sealed interface EmailSentResult {
    data class Success(val mesage: String) : EmailSentResult
    data class Failed(val exception: Exception) : EmailSentResult
}
