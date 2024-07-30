package com.bitroot.trainee.restapi.domain.file.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.file.FileDetailsService
import com.bitroot.trainee.restapi.domain.file.ProfileImageService
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ProfileImageDto
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
@RequestMapping("/api/image")
class ProfileImageController(
    val profileImageService: ProfileImageService,
) {

    @PostMapping("/upload/{schoolId}")
    fun uploadFile(
        @RequestParam("files") file: MultipartFile,
        @PathVariable schoolId: Long,
    ): ResponseEntity<MessageBody> =
        ResponseEntity.ok(
            profileImageService
                .uploadProfileImage(
                    file,
                    schoolId,
                ).toMessageBody(),
        )

    @GetMapping("/stream/{schoolId}")
    fun getProfileImageBySchoolId(
        @PathVariable schoolId: Long,
    ): ResponseEntity<ProfileImageDto> = ResponseEntity.ok(profileImageService.getProfileImageBySchoolId(schoolId))

    @GetMapping("/content/{contentName}")
    fun getContent(
        @PathVariable contentName: String,
    ): ResponseEntity<Resource> = profileImageService.prepareContent(contentName)

}
