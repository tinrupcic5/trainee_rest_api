package com.bitroot.trainee.restapi.domain.file.common.interfaces

import java.time.LocalDateTime

data class FileUriResponse(
    val type: String,
    val classPath: String? = null,
    val name: String? = null,
    val uri: String? = null,
    val suffix: String? = null,
    val contentComment: String? = null,
    val notificationMessage: String? = null,
    val createdAt: LocalDateTime,
)
