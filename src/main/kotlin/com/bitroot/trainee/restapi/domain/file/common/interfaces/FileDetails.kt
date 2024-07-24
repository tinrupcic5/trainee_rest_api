package com.bitroot.trainee.restapi.domain.file.common.interfaces

import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa.FileDetailsEntity
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.FileDetailsDto
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.Section
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.toEntity
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

data class FileDetails(
    val id: FileDetailsId? = null,
    val name: FileDetailsName,
    val section: Section,
    val fileViewStatus: FileViewStatus,
    val createdAt: LocalDateTime,
    val fileType: FileDetailsType,
    val comment: FileComment,
)

fun FileDetails.toDto(originalFileNameWithExtension: String? = null): FileDetailsDto =
    FileDetailsDto(
        id = this.id?.value,
        name = this.name.value,
        section = section.toDto(),
        fileViewStatus = this.fileViewStatus,
        createdAt = this.createdAt,
        fileType = this.fileType.value,
        originalFileNameWithExtension = originalFileNameWithExtension,
        comment = this.comment.value,
    )

fun FileDetails.toEntity(): FileDetailsEntity =
    FileDetailsEntity(
        id = this.id?.value,
        name = this.name.value,
        section = this.section.toEntity(),
        fileViewStatus = this.fileViewStatus.name,
        createdAt = this.createdAt,
        fileType = this.fileType.value,
        comment = this.comment.value,
    )
