package com.bitroot.trainee.restapi.domain.membership.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.web.MembershipFeeAmountDto
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.web.UserDetailsDto

data class MembershipFeeDto(
    val id: Long? = null,
    val userDetails: UserDetailsDto,
    val paymentDate: String,
    val validUntil: String? = null,
    val forMonth: Int,
    val membershipFeeAmount: MembershipFeeAmountDto,
    val isPaid: Boolean,
    val isEmailSent: Boolean,
    val emailSentDate: String? = null,
)
