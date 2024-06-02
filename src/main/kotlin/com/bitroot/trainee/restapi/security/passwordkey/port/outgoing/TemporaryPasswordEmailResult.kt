package com.bitroot.trainee.restapi.security.passwordkey.port.outgoing

import com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.web.TemporaryPasswordEmailDto

sealed interface TemporaryPasswordEmailResult {
    data class Success(val passwordResetKeyEmailDto: TemporaryPasswordEmailDto) : TemporaryPasswordEmailResult
    data class Failed(val exception: Exception) : TemporaryPasswordEmailResult
}
