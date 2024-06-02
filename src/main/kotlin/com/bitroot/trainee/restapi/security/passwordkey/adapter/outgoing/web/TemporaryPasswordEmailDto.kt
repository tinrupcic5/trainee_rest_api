package com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.web

data class TemporaryPasswordEmailDto(
    val to: String,
    val name: String,
    val password: String,
    val message: String,
)
