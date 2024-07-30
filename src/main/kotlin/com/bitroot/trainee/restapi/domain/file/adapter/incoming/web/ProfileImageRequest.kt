package com.bitroot.trainee.restapi.domain.file.adapter.incoming.web

import java.time.LocalDateTime

data class ProfileImageRequest(
    val name: String,
    val classPath: String,
    val schoolId: Long,
    val suffix: String,
    val createdAt: LocalDateTime,
)
