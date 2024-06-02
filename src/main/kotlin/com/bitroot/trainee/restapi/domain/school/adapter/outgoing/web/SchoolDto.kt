package com.bitroot.trainee.restapi.domain.school.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.school.common.interfaces.School
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolId
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolName

data class SchoolDto(
    val id: Long?,
    val schoolName: String,
)

fun SchoolDto.toDomain(): School =
    School(
        schoolId = SchoolId(this.id),
        schoolName = SchoolName(this.schoolName),
    )
