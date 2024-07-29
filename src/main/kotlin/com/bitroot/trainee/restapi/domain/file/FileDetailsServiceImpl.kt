package com.bitroot.trainee.restapi.domain.file

import com.bitroot.trainee.restapi.appsettings.AppSettings
import com.bitroot.trainee.restapi.appsettings.isVideo
import com.bitroot.trainee.restapi.domain.file.adapter.incoming.web.FileDetailsRequest
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa.FileDetailsRepository
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ResourceDto
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileComment
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetails
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsClassPath
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsId
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsName
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsNameSuffix
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsType
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileUriResponse
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileViewStatus
import com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.jpa.SectionRepository
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.toRequest
import com.bitroot.trainee.restapi.domain.file.properties.FileProperties
import com.bitroot.trainee.restapi.domain.notification.adapter.outgoing.jpa.NotificationRepository
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsRepository
import mu.KotlinLogging
import org.apache.commons.lang3.StringUtils.substringAfterLast
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.UUID

@Service
@Validated
class FileDetailsServiceImpl(
    val fileDetailsRepository: FileDetailsRepository,
    val notificationRepository: NotificationRepository,
    val userDetailsRepository: UserDetailsRepository,
    val fileProperties: FileProperties,
    val sectionRepository: SectionRepository,
    val resourceLoader: ResourceLoader,
) : FileDetailsService {
    private val logger = KotlinLogging.logger { }

    override fun save(fileDetailsRequest: FileDetailsRequest): String {
        val sectionResult = sectionRepository.getSectionById(fileDetailsRequest.section.id!!)
        return fileDetailsRepository.save(
            FileDetails(
                id = FileDetailsId(fileDetailsRequest.id?.value),
                name = FileDetailsName(fileDetailsRequest.name),
                section = sectionResult!!,
                classPath = FileDetailsClassPath(fileDetailsRequest.classPath.value),
                suffix = FileDetailsNameSuffix(fileDetailsRequest.suffix.value),
                fileViewStatus = fileDetailsRequest.fileViewStatus,
                createdAt = fileDetailsRequest.createdAt,
                fileType = FileDetailsType(fileDetailsRequest.fileType),
                comment = FileComment(fileDetailsRequest.comment),
            ),
        )
    }

    override fun delete(fileDetailsId: Long): String {
        val fileDetails = fileDetailsRepository.getFileDetailsById(fileDetailsId)

        if (!fileDetailsRepository
                .delete(
                    fileDetails,
                ).contains("deleted")
        ) {
            return "File not deleted"
        }

        return fileProperties.deleteFile(
            schoolName = fileDetails.section.userDetails.schoolDetails.school.schoolName.value,
            sectionName = fileDetails.section.name.value,
            videoNameToDelete = fileDetails.name.value,
            fileType = fileDetails.fileType.value,
        )
    }

    override fun streamFileAndGetNotifications(userId: Long): List<FileUriResponse> {
        val sections = sectionRepository.getAllSections(userId)
        val userDetails = userDetailsRepository.getUserDetailsByUserId(userId)
        val notification = notificationRepository.getNotificationForSchoolDetailsId(userDetails.schoolDetails.id?.value!!)

        val fileDetails: List<FileDetails> = fileDetailsRepository.getFileDetailsBySectionId(sections)

        val fileResponses: List<FileUriResponse> = fileProperties.streamFile(fileDetails)

        val responses = mutableListOf<FileUriResponse>()

        responses.addAll(fileResponses)
        notification.forEach {
            responses.add(
                FileUriResponse(
                    type = AppSettings.NOTIFICATION,
                    notificationMessage = it.message.value,
                    createdAt = it.createdAt,
                ),
            )
        }

        return responses.sortedBy { it.createdAt }
    }

    override fun getAllSections(userId: Long): ResponseEntity<Set<ResourceDto>> {
        val sections = sectionRepository.getAllSections(userId)
        val fileDetails = fileDetailsRepository.getFileDetailsBySectionId(sections)

        val downloadedFiles =
            fileProperties.downloadFiles(
                fileDetails,
            )
        return ResponseEntity.ok(downloadedFiles.sortedByDescending { it.createdAt }.toSet())
    }

    override fun downloadFile(fileName: String): ResponseEntity<Resource> = fileProperties.downloadFile(fileName)

    override fun uploadFile(
        file: MultipartFile,
        sectionId: Long,
        userDetailsId: Long,
        viewStatus: FileViewStatus,
        comment: String,
    ): String {
        val sectionResult = sectionRepository.getSectionById(sectionId) ?: return "Section not found"

        val newFileName = createCustomUUID(LocalDateTime.now().toString())

        val uploadFile =
            fileProperties.uploadFile(
                sectionResult.userDetails.schoolDetails.school.schoolName.value,
                sectionResult.name.value,
                file,
                newFileName,
            )
        return save(
            FileDetailsRequest(
                id = null,
                name = newFileName,
                section = sectionResult.toRequest(),
                classPath = FileDetailsClassPath(uploadFile),
                suffix = FileDetailsNameSuffix("." + file.originalFilename.toString().substringAfterLast('.')),
                fileViewStatus = viewStatus,
                createdAt = LocalDateTime.now(),
                fileType = if (isVideo(file)) AppSettings.VIDEO else AppSettings.IMAGE,
                comment = comment,
            ),
        )
    }

    fun createCustomUUID(createdAt: String): String {
        val uuid = UUID.randomUUID().toString().substring(0, 14)
        val uuid2 = UUID.randomUUID().toString().substring(0, 5)
        val dates = createdAt.substring(createdAt.length - 5)
        return "$uuid$dates$uuid2"
    }

    override fun renameFile(
        fileId: Long,
        newFileName: String,
    ): String {
        val file = fileDetailsRepository.getFileDetailsById(fileId)
        fileDetailsRepository.save(file.copy(name = FileDetailsName(newFileName)))
        logger.info("File details saved: $newFileName")
        return fileProperties.renameFile(
            file.section.userDetails.schoolDetails.school.schoolName.value,
            file.section.name.value,
            file.name.value,
            newFileName,
        )
    }

//    override fun prepareContent(title: String): Mono<Resource> {
//        val fileDetails = fileDetailsRepository.getFileDetailsByName(title.substringBeforeLast("."))
//        val resourcePath = "classpath:${fileDetails.classPath.value}"
//        println("Loading resource from path: $resourcePath")
//        return Mono.fromSupplier {
//            resourceLoader.getResource(resourcePath)
//        }
//    }

    override fun prepareContent(contentName: String): ResponseEntity<Resource> {
        val fileDetails = fileDetailsRepository.getFileDetailsByName(contentName.substringBeforeLast("."))

        val resourcePath = "classpath:${fileDetails.classPath.value}"
        val resource: Resource = resourceLoader.getResource(resourcePath)

        val contentType =
            when {
                contentName.endsWith(".mp4") -> "video/mp4"
                contentName.endsWith(".mov") -> "video/quicktime"
                contentName.endsWith(".png") -> "image/png"
                contentName.endsWith(".jpg") || contentName.endsWith(".jpeg") -> "image/jpeg"
                else -> "application/octet-stream"
            }

        return if (resource.exists() || resource.isReadable) {
            ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build<Resource>()
        }
    }
}
