package com.bitroot.trainee.restapi.domain.school.details

import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.SchoolDetailsRepository
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class SchoolDetailsServiceImpl(
    val schoolDetailsRepository: SchoolDetailsRepository,

) : SchoolDetailsService {
    override fun getSchoolDetailsById(schoolDetailsId: Long): SchoolDetails =
        schoolDetailsRepository.getSchoolDetailsById(schoolDetailsId)
}
