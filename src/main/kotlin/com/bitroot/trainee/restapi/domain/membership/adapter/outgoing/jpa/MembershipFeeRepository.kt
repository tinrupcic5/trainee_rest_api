package com.bitroot.trainee.restapi.domain.membership.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFee

interface MembershipFeeRepository {
    fun payMembership(membershipFee: MembershipFee): String
    fun membershipCheck(): List<MembershipFee>
    fun membershipCheckForUnpaidFeeOverMonthAndTenDays(): List<MembershipFee>
    fun saveMembershipFeeEmailList(emailList: List<MembershipFee>): String
    fun getAllMembershipBySchool(schoolId: Long): List<MembershipFee>
    fun getMembershipById(membershipFeeId: Long): MembershipFee
    fun getAllMembershipForExactMonth(year: Int, month: Int, schoolId: Long): List<MembershipFee>
    fun findByUserAndLastPaymentDate(userDetailsId: Long, isPaid: Boolean): MembershipFee?
    fun deletePayment(membershipFeeId: Long): String
}
