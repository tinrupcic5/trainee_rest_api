package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileViewStatus
import com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.web.SectionDto
import java.time.LocalDateTime

data class FileDetailsDto(
    val id: Long? = null,
    val name: String,
    val section: SectionDto,
    val fileViewStatus: FileViewStatus,
    val createdAt: LocalDateTime,
    val fileType: String,
    val originalFileNameWithExtension: String? = null,
)
