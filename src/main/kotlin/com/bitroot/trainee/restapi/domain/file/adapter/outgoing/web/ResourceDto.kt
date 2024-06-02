package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileViewStatus
import com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.web.SectionDto
import java.time.LocalDateTime

data class ResourceDto(
    val fileDetails: FileDetailsDto,
    val fileViewStatus: FileViewStatus,
    val createdAt: LocalDateTime,
    val section: SectionDto,
)
