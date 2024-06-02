package com.bitroot.trainee.restapi.domain.file

import com.bitroot.trainee.restapi.appsettings.AppSettings
import com.bitroot.trainee.restapi.appsettings.isVideo
import com.bitroot.trainee.restapi.domain.file.adapter.incoming.web.FileDetailsRequest
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa.FileDetailsRepository
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ResourceDto
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetails
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsId
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsName
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsType
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileViewStatus
import com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.jpa.SectionRepository
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.toRequest
import com.bitroot.trainee.restapi.domain.file.properties.FileProperties
import mu.KotlinLogging
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
@Validated
class FileDetailsServiceImpl(
    val fileDetailsRepository: FileDetailsRepository,
    val fileProperties: FileProperties,
    val sectionRepository: SectionRepository,
) : FileDetailsService {
    private val logger = KotlinLogging.logger { }

    override fun save(fileDetailsRequest: FileDetailsRequest): String {
        val sectionResult = sectionRepository.getSectionById(fileDetailsRequest.section.id!!)
        return fileDetailsRepository.save(
            FileDetails(
                id = FileDetailsId(fileDetailsRequest.id?.value),
                name = FileDetailsName(fileDetailsRequest.name),
                section = sectionResult,
                fileViewStatus = fileDetailsRequest.fileViewStatus,
                createdAt = fileDetailsRequest.createdAt,
                fileType = FileDetailsType(fileDetailsRequest.fileType),
            ),
        )
    }

    override fun delete(fileDetailsId: Long): String {
        val fileDetails = fileDetailsRepository.getFileDetailsById(fileDetailsId)

        if (!fileDetailsRepository.delete(
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

    override fun streamFile(userId: Long): ResponseEntity<Resource> {
        val sections = sectionRepository.getAllSections(userId)
        val fileDetails = fileDetailsRepository.getFileDetailsBySectionId(sections)
        return fileProperties.streamFile(fileDetails.get(0))

    }

    override fun getAllSections(userId: Long): ResponseEntity<Set<ResourceDto>> {
        val sections = sectionRepository.getAllSections(userId)
        val fileDetails = fileDetailsRepository.getFileDetailsBySectionId(sections)

        val downloadedFiles = fileProperties.downloadFiles(
            fileDetails,
        )
        return ResponseEntity.ok(downloadedFiles.sortedByDescending { it.createdAt }.toSet())
    }

    override fun downloadFile(fileName: String): ResponseEntity<Resource> =
        fileProperties.downloadFile(fileName)

    override fun uploadFile(file: MultipartFile, sectionId: Long, viewStatus: FileViewStatus): String {
        val sectionResult = sectionRepository.getSectionById(sectionId)

        if (!save(
                FileDetailsRequest(
                    id = null,
                    name = file.originalFilename?.substringBeforeLast('.')!!,
                    section = sectionResult.toRequest(),
                    fileViewStatus = viewStatus,
                    createdAt = LocalDateTime.now(),
                    fileType = if (isVideo(file)) AppSettings.VIDEO else AppSettings.IMAGE,
                ),
            ).contains("saved")
        ) {
            return "File not saved"
        }
        return fileProperties.uploadFile(
            sectionResult.userDetails.schoolDetails.school.schoolName.value,
            sectionResult.name.value,
            file,
        )
    }

    override fun renameFile(fileId: Long, newFileName: String): String {
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
}
