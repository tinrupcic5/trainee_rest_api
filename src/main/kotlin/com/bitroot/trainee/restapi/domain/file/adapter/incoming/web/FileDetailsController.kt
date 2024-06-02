package com.bitroot.trainee.restapi.domain.file.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.file.FileDetailsService
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ResourceDto
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileViewStatus
import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.io.InputStreamResource
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream

@RestController
@RequestMapping("/api/filedetails")
class FileDetailsController(
    val fileDetailsService: FileDetailsService,
) {

//    @GetMapping("/download/{fileName:.+}")
//    fun downloadFile(@PathVariable fileName: String) =
//        fileDetailsService.downloadFile(fileName)

    @PostMapping("/upload/{sectionId}/{viewStatus}")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile,
        @PathVariable sectionId: Long,
        @PathVariable viewStatus: String,
    ): ResponseEntity<MessageBody> =
        ResponseEntity.ok(
            fileDetailsService.uploadFile(file, sectionId, FileViewStatus.valueOf(viewStatus)).toMessageBody(),
        )

    @PutMapping("/rename/{fileId}/{newFileName}")
    fun renameFileName(@PathVariable fileId: Long, @PathVariable newFileName: String): ResponseEntity<MessageBody> =
        ResponseEntity.ok(
            fileDetailsService.renameFile(fileId, newFileName).toMessageBody(),
        )

    @DeleteMapping("/{fileId}")
    fun deleteFileDetails(@PathVariable fileId: Long): ResponseEntity<MessageBody> =
        ResponseEntity.ok(
            fileDetailsService.delete(
                fileId,
            ).toMessageBody(),
        )

    //    @RolesAllowed("VIEWER")
    @GetMapping("/{userId}")
    fun getAllSectionsAndFileDetails(
        @PathVariable userId: Long
    ): ResponseEntity<Set<ResourceDto>> =
        fileDetailsService.getAllSections(userId)

    @GetMapping("/stream/{userId}/video")
    fun streamVideoFile(@PathVariable userId: Long): ResponseEntity<Resource> =
        fileDetailsService.streamFile(userId)
}
