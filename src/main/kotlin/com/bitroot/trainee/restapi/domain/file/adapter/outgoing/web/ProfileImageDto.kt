package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.web.SchoolDto
import java.time.LocalDateTime

data class ProfileImageDto(
    val id: Long? = null,
    val name: String,
    val school: SchoolDto,
    val uri: String? = null,
    val suffix: String,
    val classPath: String,
    val createdAt: LocalDateTime,
)
