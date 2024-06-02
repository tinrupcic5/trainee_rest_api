package com.bitroot.trainee.restapi.domain.membership.details

import com.bitroot.trainee.restapi.domain.membership.details.adapter.incoming.web.MembershipFeeAmountRequest
import com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.jpa.MembershipFeeAmountRepository
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.Amount
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmount
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmountCurrency
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class MembershipFeeAmountServiceImpl(
    val membershipFeeAmountRepository: MembershipFeeAmountRepository,
) : MembershipFeeAmountService {
    override fun save(membershipFeeAmount: MembershipFeeAmountRequest): String {
        val membershipAmount = membershipFeeAmountRepository.getMembershipAmountById(membershipFeeAmount.id!!)
        return membershipFeeAmountRepository.save(
            membershipAmount.copy(
                amount = Amount(membershipFeeAmount.amount),
                currency = MembershipFeeAmountCurrency(membershipFeeAmount.currency),
            ),
        )
    }

    override fun getAllAmountForSchoolDetailsId(schoolDetailsId: Long): MembershipFeeAmount =
        membershipFeeAmountRepository.getAllAmountForSchoolDetailsId(schoolDetailsId)
}
