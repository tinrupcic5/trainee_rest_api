package com.bitroot.trainee.restapi.domain.school.common.interfaces

import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.jpa.SchoolEntity
import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.web.SchoolDto

data class School(
    val schoolId: SchoolId? = null,
    val schoolName: SchoolName,
)

fun School.domainToEntity(): SchoolEntity =
    SchoolEntity(
        id = this.schoolId?.value,
        schoolName = this.schoolName.value,
    )

fun School.domainToDto(): SchoolDto =
    SchoolDto(
        id = this.schoolId?.value,
        schoolName = this.schoolName.value,
    )
