package com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.web

data class PasswordResetKeyEmailDto(
    val to: String,
    val name: String,
    val key: String,
    val expires: String,
    val message: String,
)
