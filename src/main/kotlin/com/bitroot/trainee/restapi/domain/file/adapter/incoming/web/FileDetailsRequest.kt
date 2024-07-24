package com.bitroot.trainee.restapi.domain.file.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsId
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileViewStatus
import com.bitroot.trainee.restapi.domain.file.details.section.adapter.incoming.web.SectionRequest
import java.time.LocalDateTime

data class FileDetailsRequest(
    val id: FileDetailsId? = null,
    val name: String,
    val section: SectionRequest,
    val fileViewStatus: FileViewStatus,
    val createdAt: LocalDateTime,
    val fileType: String,
    val comment: String,
)
