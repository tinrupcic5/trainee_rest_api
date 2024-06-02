package com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.web

import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.Amount
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmount
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmountCurrency
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmountId
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.web.SchoolDetailsDto
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.web.toDomain
import java.math.BigDecimal

data class MembershipFeeAmountDto(
    val id: Long? = null,
    val schoolDetails: SchoolDetailsDto,
    val amount: BigDecimal,
    val currency: String,
)
fun MembershipFeeAmountDto.toDomain() =
    MembershipFeeAmount(
        id = MembershipFeeAmountId(this.id),
        schoolDetails = this.schoolDetails.toDomain(),
        amount = Amount(this.amount),
        currency = MembershipFeeAmountCurrency(this.currency),
    )
