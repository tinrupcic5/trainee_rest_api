package com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.web.UserDetailsDto
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserLoginTokenDto(
    val userDetails: UserDetailsDto,
    val token: String,
    val qrData: String? = null,
    val refreshToken: String? = null,
)
