package com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmount
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.toEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class MembershipFeeAmountRepositoryImpl(
    val membershipFeeAmountEntityJpaRepository: MembershipFeeAmountEntityJpaRepository,
) : MembershipFeeAmountRepository {

    @Modifying
    override fun save(membershipFeeAmount: MembershipFeeAmount): String {
        membershipFeeAmountEntityJpaRepository.save(membershipFeeAmount.toEntity())
        return "MembershipFeeAmount saved."
    }

    override fun getMembershipAmountById(id: Long): MembershipFeeAmount =
        membershipFeeAmountEntityJpaRepository.getReferenceById(id).toDomain()

    override fun getAllAmountForSchoolDetailsId(schoolDetailsId: Long): MembershipFeeAmount =
        membershipFeeAmountEntityJpaRepository.getAllAmountForSchoolDetailsId(schoolDetailsId).toDomain()
}
