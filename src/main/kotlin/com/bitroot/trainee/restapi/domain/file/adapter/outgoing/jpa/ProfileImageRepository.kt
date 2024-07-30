package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImage

interface ProfileImageRepository {
    fun save(fileDetails: ProfileImage): String

    fun delete(fileDetails: ProfileImage): String

    fun getProfileImageBySchoolId(schoolId: Long): ProfileImage

    fun getProfileImageById(id: Long): ProfileImage
    fun getProfileImageByName(name: String): ProfileImage
}
