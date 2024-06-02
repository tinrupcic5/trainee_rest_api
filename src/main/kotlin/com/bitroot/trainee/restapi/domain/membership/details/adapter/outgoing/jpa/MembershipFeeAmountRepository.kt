package com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmount

interface MembershipFeeAmountRepository {

    fun save(membershipFeeAmount: MembershipFeeAmount): String
    fun getMembershipAmountById(id: Long): MembershipFeeAmount
    fun getAllAmountForSchoolDetailsId(schoolDetailsId: Long): MembershipFeeAmount
}
