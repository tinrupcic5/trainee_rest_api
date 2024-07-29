package com.bitroot.trainee.restapi.domain.file.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.file.FileDetailsService
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ResourceDto
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileComment
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileUriResponse
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileViewStatus
import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/filedetails")
class FileDetailsController(
    val fileDetailsService: FileDetailsService,
) {
    //    @GetMapping("/download/{fileName:.+}")
//    fun downloadFile(@PathVariable fileName: String) =
//        fileDetailsService.downloadFile(fileName)

    @PostMapping("/upload/{sectionId}/{userDetailsId}/{viewStatus}")
    fun uploadFile(
        @RequestParam("files") file: MultipartFile,
        @PathVariable sectionId: Long,
        @PathVariable userDetailsId: Long,
        @PathVariable viewStatus: String,
        @RequestParam("comment") comment: FileComment,
    ): ResponseEntity<MessageBody> =
        ResponseEntity.ok(
            fileDetailsService
                .uploadFile(
                    file,
                    sectionId,
                    userDetailsId,
                    FileViewStatus.valueOf(viewStatus),
                    comment.value,
                ).toMessageBody(),
        )

    @PutMapping("/rename/{fileId}/{newFileName}")
    fun renameFileName(
        @PathVariable fileId: Long,
        @PathVariable newFileName: String,
    ): ResponseEntity<MessageBody> =
        ResponseEntity.ok(
            fileDetailsService.renameFile(fileId, newFileName).toMessageBody(),
        )

    @DeleteMapping("/{fileId}")
    fun deleteFileDetails(
        @PathVariable fileId: Long,
    ): ResponseEntity<MessageBody> =
        ResponseEntity.ok(
            fileDetailsService
                .delete(
                    fileId,
                ).toMessageBody(),
        )

    //    @RolesAllowed("VIEWER")
    @GetMapping("/{userId}")
    fun getAllSectionsAndFileDetails(
        @PathVariable userId: Long,
    ): ResponseEntity<Set<ResourceDto>> = fileDetailsService.getAllSections(userId)

    @GetMapping("/stream/{userId}")
    fun getAllFilesAndNotifications(
        @PathVariable userId: Long,
    ): ResponseEntity<List<FileUriResponse>> = ResponseEntity.ok(fileDetailsService.streamFileAndGetNotifications(userId))

    @GetMapping("/content/{contentName}")
    fun getVideo(
        @PathVariable contentName: String,
    ): ResponseEntity<Resource> = fileDetailsService.prepareContent(contentName)

//    @GetMapping("/content/{contentName}", produces = ["video/mp4", "video/quicktime", "image/png", "image/jpeg"])
//    fun streamVideo(
//        @PathVariable("contentName") contentName: String,
//    ): Mono<Resource> = fileDetailsService.prepareContent(contentName)
}
