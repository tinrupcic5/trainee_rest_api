package com.bitroot.trainee.restapi.domain.user.adapter.incoming.web

data class UserChangePasswordRequest(
    val userId: Long,
    val newPassword: String,
    val passwordResetKey: String? = null,
)
