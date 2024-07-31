package com.bitroot.trainee.restapi.domain.file

import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa.ProfileImageRepository
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ProfileImageDto
import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImage
import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImageName
import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImageNameClassPath
import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImageNameSuffix
import com.bitroot.trainee.restapi.domain.file.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.file.properties.FileProperties
import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.jpa.SchoolRepository
import mu.KotlinLogging
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*

@Service
@Validated
class ProfileImageServiceImpl(
    val profileImageRepository: ProfileImageRepository,
    val schoolRepository: SchoolRepository,
    val fileProperties: FileProperties,
    val resourceLoader: ResourceLoader,
    ) : ProfileImageService {
    private val logger = KotlinLogging.logger { }

    override fun uploadProfileImage(
        file: MultipartFile,
        schoolId: Long,
    ): String {
        val school = schoolRepository.getSchoolById(schoolId)
        logger.info("Saving profileImage of ${school!!.schoolName}")
        val newFileName = createCustomUUID(LocalDateTime.now().toString())
        val uploadFile =
            fileProperties.uploadProfileImage(
                school.schoolName.value,
                file,
                newFileName,
            )
        return profileImageRepository.save(
            ProfileImage(
                school = school,
                name = ProfileImageName(newFileName),
                suffix = ProfileImageNameSuffix("." + file.originalFilename.toString().substringAfterLast('.')),
                classPath = ProfileImageNameClassPath(uploadFile),
                createdAt = LocalDateTime.now(),
            ),
        )
    }

    override fun streamProfilePicture(schoolId: Long): String {
        val image = profileImageRepository.getProfileImageBySchoolId(schoolId)
        val school = schoolRepository.getSchoolById(schoolId)
       return  fileProperties.getProfileImage(image.name.value,school!!.schoolName.value)
    }

    override fun delete(profileImageId: Long): String {
        val profileImage = profileImageRepository.getProfileImageById(profileImageId)
        logger.info("Deleting profileImage of ${profileImage.school.schoolName}")
        return profileImageRepository.delete(profileImage)
    }

    override fun getProfileImageBySchoolId(schoolId: Long): ProfileImageDto {
      val file =   profileImageRepository.getProfileImageBySchoolId(schoolId).toDto()

        val uri = fileProperties.getProfileImage(file.name,file.school.schoolName)

        return  file.copy(uri = uri)
    }


    override fun prepareContent(contentName: String): ResponseEntity<Resource> {
        val profileImage = profileImageRepository.getProfileImageByName(contentName.substringBeforeLast("."))

        val resourcePath = "classpath:${profileImage.classPath.value}"
        val resource: Resource = resourceLoader.getResource(resourcePath)
        logger.info("resourcePath $resourcePath")
        val contentType =
            when {
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
            logger.info("HttpStatus.NOT_FOUND")
            ResponseEntity.status(HttpStatus.NOT_FOUND).build<Resource>()
        }
    }
}

fun createCustomUUID(createdAt: String): String {
    val uuid = UUID.randomUUID().toString().substring(0, 14)
    val uuid2 = UUID.randomUUID().toString().substring(0, 5)
    val dates = createdAt.substring(createdAt.length - 5)
    return "$uuid$dates$uuid2"
}
