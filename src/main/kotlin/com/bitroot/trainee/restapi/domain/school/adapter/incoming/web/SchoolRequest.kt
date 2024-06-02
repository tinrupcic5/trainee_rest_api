package com.bitroot.trainee.restapi.domain.school.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.school.common.interfaces.School
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolId
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolName

data class SchoolRequest(
    val schoolId: Long? = null,
    val schoolName: String,
)

fun SchoolRequest.requestToDomain(): School =
    School(
        schoolId = SchoolId(this.schoolId),
        schoolName = SchoolName(this.schoolName),
    )
