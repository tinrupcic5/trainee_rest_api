package com.bitroot.trainee.restapi.domain.file.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.file.ProfileImageService
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ProfileImageDto
import com.bitroot.trainee.restapi.domain.user.MessageBody
import mu.KotlinLogging
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/image")
class ProfileImageController(
    val profileImageService: ProfileImageService,
) {
    private val logger = KotlinLogging.logger { }

    @PostMapping("/upload/{schoolId}")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile,
        @PathVariable schoolId: Long,
    ): ResponseEntity<MessageBody> {
        logger.info("Uploading file By SchoolId : $schoolId")
        return    ResponseEntity.ok(
            profileImageService
                .uploadProfileImage(
                    file,
                    schoolId,
                ).toMessageBody(),
        )
    }


    @GetMapping("/stream/{schoolId}")
    fun getProfileImageBySchoolId(
        @PathVariable schoolId: Long,
    ): ResponseEntity<ProfileImageDto>
        {
            logger.info("Get Profile Image By SchoolId : $schoolId")
        return ResponseEntity.ok(profileImageService.getProfileImageBySchoolId(schoolId))}

    @GetMapping("/content/{contentName}")
    fun getContent(
        @PathVariable contentName: String,
    ): ResponseEntity<Resource> = profileImageService.prepareContent(contentName)

}
