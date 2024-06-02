package com.bitroot.trainee.restapi.domain.membership.details

import com.bitroot.trainee.restapi.domain.membership.details.adapter.incoming.web.MembershipFeeAmountRequest
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmount

interface MembershipFeeAmountService {
    fun save(membershipFeeAmount: MembershipFeeAmountRequest): String
    fun getAllAmountForSchoolDetailsId(schoolId: Long): MembershipFeeAmount
}
