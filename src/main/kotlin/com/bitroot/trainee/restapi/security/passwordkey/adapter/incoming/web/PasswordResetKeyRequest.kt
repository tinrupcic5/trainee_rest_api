package com.bitroot.trainee.restapi.security.passwordkey.adapter.incoming.web

data class PasswordResetKeyRequest(
    val id: Long? = null,
    val userId: Long,
    val email: String? = null,
    val resetKey: String? = null,
)
