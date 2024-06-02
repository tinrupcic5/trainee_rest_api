package com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails

interface SchoolDetailsRepository {

    fun getSchoolDetailsById(schoolDetailsId: Long): SchoolDetails
}
