package com.bitroot.trainee.restapi.domain.file

import com.bitroot.trainee.restapi.domain.file.adapter.incoming.web.FileDetailsRequest
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ResourceDto
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileUriResponse
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileViewStatus
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface FileDetailsService {
    fun save(fileDetailsRequest: FileDetailsRequest): String

    fun delete(fileDetailsId: Long): String

    fun streamFileAndGetNotifications(userId: Long): List<FileUriResponse>

    fun getAllSections(userId: Long): ResponseEntity<Set<ResourceDto>>

    fun downloadFile(fileName: String): ResponseEntity<Resource>

    fun uploadFile(
        file: MultipartFile,
        sectionId: Long,
        userDetailsId: Long,
        viewStatus: FileViewStatus,
        comment: String,
    ): String

    fun renameFile(
        fileId: Long,
        newFileName: String,
    ): String

    fun prepareContent(contentName: String): ResponseEntity<Resource>
}
