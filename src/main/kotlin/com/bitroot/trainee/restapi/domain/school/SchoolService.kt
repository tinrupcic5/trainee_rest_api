package com.bitroot.trainee.restapi.domain.school

import com.bitroot.trainee.restapi.domain.school.common.interfaces.School

interface SchoolService {

    fun getAllSchool(): School?
    fun getSchoolById(schoolId: Long): School?
    fun saveOrUpdate(school: School): String
}
