package com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.Amount
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmount
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmountCurrency
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmountId
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.SchoolDetailsEntity
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.toDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "membership_fee_amount")
data class MembershipFeeAmountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "school_details_id")
    val schoolDetails: SchoolDetailsEntity,

    @Column(name = "amount", precision = 10, scale = 2)
    val amount: BigDecimal,

    @JoinColumn(name = "currency")
    val currency: String,
)

fun MembershipFeeAmountEntity.toDomain(): MembershipFeeAmount =
    MembershipFeeAmount(
        id = MembershipFeeAmountId(this.id),
        schoolDetails = this.schoolDetails.toDomain(),
        amount = Amount(this.amount),
        currency = MembershipFeeAmountCurrency(this.currency),
    )
