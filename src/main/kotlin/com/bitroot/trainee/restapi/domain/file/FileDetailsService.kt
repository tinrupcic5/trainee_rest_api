package com.bitroot.trainee.restapi.domain.file

import com.bitroot.trainee.restapi.domain.file.adapter.incoming.web.FileDetailsRequest
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ResourceDto
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileViewStatus
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface FileDetailsService {

    fun save(fileDetailsRequest: FileDetailsRequest): String
    fun delete(fileDetailsId: Long): String
    fun streamFile(userId: Long): ResponseEntity<Resource>
    fun getAllSections(userId: Long): ResponseEntity<Set<ResourceDto>>
    fun downloadFile(fileName: String): ResponseEntity<Resource>
    fun uploadFile(file: MultipartFile, sectionId: Long, viewStatus: FileViewStatus): String
    fun renameFile(fileId: Long, newFileName: String): String
}
