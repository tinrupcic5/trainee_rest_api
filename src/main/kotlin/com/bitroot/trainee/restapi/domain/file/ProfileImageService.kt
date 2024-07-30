package com.bitroot.trainee.restapi.domain.file

import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ProfileImageDto
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface ProfileImageService {
    fun uploadProfileImage(
        file: MultipartFile,
        schoolId: Long,
    ): String

    fun streamProfilePicture(schoolId: Long): String

    fun delete(fileDetailsId: Long): String

    fun getProfileImageBySchoolId(schoolId: Long): ProfileImageDto

     fun prepareContent(contentName: String): ResponseEntity<Resource>
}
