package com.bitroot.trainee.restapi.domain.file.details.section.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web.UserDetailsRegisterRequest

data class SectionRequest(
    val id: Long? = null,
    val name: String,
    val userDetails: UserDetailsRegisterRequest,
)
