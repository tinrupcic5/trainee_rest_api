package com.bitroot.trainee.restapi.domain.school.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.school.common.interfaces.School

interface SchoolRepository {

    fun getAllSchool(): School?

    fun getSchoolById(schoolId: Long): School?
    fun saveOrUpdate(school: School): String
}
