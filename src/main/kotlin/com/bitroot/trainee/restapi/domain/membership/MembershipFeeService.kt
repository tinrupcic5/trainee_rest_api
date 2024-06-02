package com.bitroot.trainee.restapi.domain.membership

import com.bitroot.trainee.restapi.domain.membership.adapter.incoming.web.MembershipFeeRequest
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFee

interface MembershipFeeService {
    fun payMembership(membershipFee: MembershipFeeRequest): String
    fun getAllMembershipBySchool(schoolId: Long): List<MembershipFee>
    fun membershipCheck(): String
    fun membershipCheckForUnpaidFeeOverMonthAndTenDays(): List<MembershipFee>
    fun getAllMembershipForExactMonth(year: Int, month: Int, schoolId: Long): List<MembershipFee>
}
