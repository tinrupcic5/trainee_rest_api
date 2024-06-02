package com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.file.properties.ResourceSerializer
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.web.UserDetailsDto
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.springframework.core.io.Resource

data class SectionDto(
    val id: Long? = null,
    val name: String,
    val userDetails: UserDetailsDto,
    @JsonSerialize(using = ResourceSerializer::class)
    val resource: Resource? = null,
)
