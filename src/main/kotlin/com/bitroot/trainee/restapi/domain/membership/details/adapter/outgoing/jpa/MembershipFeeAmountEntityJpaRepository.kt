package com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface MembershipFeeAmountEntityJpaRepository : JpaRepository<MembershipFeeAmountEntity, Long> {

    @Query(
        """
        SELECT * FROM membership_fee_amount where school_details_id = :schoolDetailsId
    """,
        nativeQuery = true,
    )
    fun getAllAmountForSchoolDetailsId(schoolDetailsId: Long): MembershipFeeAmountEntity
}
