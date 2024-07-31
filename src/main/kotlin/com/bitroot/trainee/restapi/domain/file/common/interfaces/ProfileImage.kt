package com.bitroot.trainee.restapi.domain.file.common.interfaces

import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa.ProfileImageEntity
import com.bitroot.trainee.restapi.domain.file.adapter.outgoing.web.ProfileImageDto
import com.bitroot.trainee.restapi.domain.school.common.interfaces.School
import com.bitroot.trainee.restapi.domain.school.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.school.common.interfaces.domainToEntity
import com.bitroot.trainee.restapi.security.passwordkey.localTimeToString
import java.time.LocalDateTime

data class ProfileImage(
    val id: ProfileImageId? = null,
    val school: School,
    val name: ProfileImageName,
    val suffix: ProfileImageNameSuffix,
    val classPath: ProfileImageNameClassPath,
    val createdAt: LocalDateTime,
)

fun ProfileImage.toDto() =
    ProfileImageDto(
        id = this.id?.value,
        name = this.name.value,
        school = this.school.domainToDto(),
        suffix = this.suffix.value,
        classPath = this.classPath.value,
        createdAt = this.createdAt,
    )

fun ProfileImage.toEntity() =
    ProfileImageEntity(
        id = this.id?.value,
        name = this.name.value,
        schoolEntity = this.school.domainToEntity(),
        suffix = this.suffix.value,
        classPath = this.classPath.value,
        createdAt = this.createdAt,
    )
