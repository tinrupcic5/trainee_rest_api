package com.bitroot.trainee.restapi.domain.school.details.common.interfaces

import com.bitroot.trainee.restapi.domain.school.adapter.incoming.web.SchoolRequest
import com.bitroot.trainee.restapi.domain.school.common.interfaces.School
import com.bitroot.trainee.restapi.domain.school.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.school.details.adapter.incoming.web.SchoolDetailsRequest
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.web.SchoolDetailsDto

data class SchoolDetails(
    val id: SchoolDetailsId? = null,
    val school: School,
    val schoolLocation: SchoolLocation,
    val schoolCountry: SchoolCountry,
)

fun SchoolDetails.toRequest(): SchoolDetailsRequest =
    SchoolDetailsRequest(
        id = this.id?.value,
        school = SchoolRequest(
            schoolId = this.school.schoolId?.value,
            schoolName = this.school.schoolName.value,
        ),
        schoolLocation = this.schoolLocation.value,
        schoolCountry = this.schoolCountry.value,
    )

fun SchoolDetails.domainToDto(): SchoolDetailsDto =
    SchoolDetailsDto(
        id = this.id?.value,
        school = this.school.domainToDto(),
        schoolLocation = this.schoolLocation.value,
        schoolCountry = this.schoolCountry.value,
    )
