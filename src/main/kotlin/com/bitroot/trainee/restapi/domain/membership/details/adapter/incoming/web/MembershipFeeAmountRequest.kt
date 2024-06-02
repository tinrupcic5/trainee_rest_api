package com.bitroot.trainee.restapi.domain.membership.details.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.Amount
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmount
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmountCurrency
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmountId
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import java.math.BigDecimal

data class MembershipFeeAmountRequest(

    val id: Long? = null,
    val schoolDetailsId: Long,
    val amount: BigDecimal,
    val currency: String,
)

fun MembershipFeeAmountRequest.toDomain(schoolDetails: SchoolDetails): MembershipFeeAmount =
    MembershipFeeAmount(
        id = MembershipFeeAmountId(this.id),
        schoolDetails = schoolDetails,
        amount = Amount(this.amount),
        currency = MembershipFeeAmountCurrency(this.currency),
    )
