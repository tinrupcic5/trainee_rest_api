package com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.web.SchoolDto
import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.web.toDomain
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolCountry
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetailsId
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolLocation

data class SchoolDetailsDto(
    val id: Long? = null,
    val school: SchoolDto,
    val schoolLocation: String,
    val schoolCountry: String,
)

fun SchoolDetailsDto.toDomain(): SchoolDetails =
    SchoolDetails(
        id = SchoolDetailsId(this.id),
        school = this.school.toDomain(),
        schoolLocation = SchoolLocation(this.schoolLocation),
        schoolCountry = SchoolCountry(this.schoolCountry),
    )
