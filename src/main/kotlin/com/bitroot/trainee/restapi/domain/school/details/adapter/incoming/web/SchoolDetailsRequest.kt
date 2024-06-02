package com.bitroot.trainee.restapi.domain.school.details.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.school.adapter.incoming.web.SchoolRequest
import com.bitroot.trainee.restapi.domain.school.common.interfaces.School
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolId
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolName
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolCountry
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetailsId
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolLocation

data class SchoolDetailsRequest(
    val id: Long? = null,
    val school: SchoolRequest,
    val schoolLocation: String,
    val schoolCountry: String,
)

fun SchoolDetailsRequest.requestToDomain(): SchoolDetails =
    SchoolDetails(
        id = SchoolDetailsId(this.id),
        school = School(
            schoolId = SchoolId(this.school.schoolId),
            schoolName = SchoolName(this.school.schoolName),
        ),
        schoolLocation = SchoolLocation(this.schoolLocation),
        schoolCountry = SchoolCountry(this.schoolCountry),
    )
