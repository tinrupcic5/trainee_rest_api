package com.bitroot.trainee.restapi.domain.school.details

import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails

interface SchoolDetailsService {
    fun getSchoolDetailsById(schoolDetailsId: Long): SchoolDetails
}
