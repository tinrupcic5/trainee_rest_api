package com.bitroot.trainee.restapi.domain.school

import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.jpa.SchoolRepository
import com.bitroot.trainee.restapi.domain.school.common.interfaces.School
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class SchoolServiceImpl(
    val schoolRepository: SchoolRepository,

) : SchoolService {
    override fun getAllSchool(): School? =
        schoolRepository.getAllSchool()

    override fun getSchoolById(schoolId: Long): School? =
        schoolRepository.getSchoolById(schoolId)

    override fun saveOrUpdate(school: School): String =
        schoolRepository.saveOrUpdate(school)
}
