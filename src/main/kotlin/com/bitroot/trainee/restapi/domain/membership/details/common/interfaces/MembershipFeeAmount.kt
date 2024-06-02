package com.bitroot.trainee.restapi.domain.membership.details.common.interfaces

import com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.jpa.MembershipFeeAmountEntity
import com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.web.MembershipFeeAmountDto
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.domainToEntity
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.domainToDto

data class MembershipFeeAmount(
    val id: MembershipFeeAmountId,
    val schoolDetails: SchoolDetails,
    val amount: Amount,
    val currency: MembershipFeeAmountCurrency,
)

fun MembershipFeeAmount.toEntity(): MembershipFeeAmountEntity =
    MembershipFeeAmountEntity(
        id = this.id.value,
        schoolDetails = this.schoolDetails.domainToEntity(),
        amount = this.amount.value,
        currency = this.currency.value,
    )

fun MembershipFeeAmount.toDto(): MembershipFeeAmountDto =
    MembershipFeeAmountDto(
        id = this.id.value,
        schoolDetails = this.schoolDetails.domainToDto(),
        amount = this.amount.value,
        currency = this.currency.value,
    )
