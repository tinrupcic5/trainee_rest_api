package com.bitroot.trainee.restapi.domain.membership.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFee
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFeeEmailSent
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFeeForMonthAndYear
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFeeId
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFeeIsPaid
import com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.jpa.MembershipFeeAmountEntity
import com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsEntity
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.entityToDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "membership_fee")
data class MembershipFeeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_details_id")
    val user: UserDetailsEntity,

    /**
     * when the fee was payed
     */
    @Column(name = "payment_date")
    val paymentDate: LocalDateTime,

    @Column(name = "valid_until")
    val validUntil: LocalDateTime,

    @Column(name = "for_month")
    val forMonth: Int,

    @ManyToOne
    @JoinColumn(name = "membership_fee_amount_id")
    val membershipFeeAmount: MembershipFeeAmountEntity,

    @Column(name = "is_paid")
    val isPaid: Boolean = false,

    @Column(name = "is_email_sent")
    val isEmailSent: Boolean = false,

    @Column(name = "email_sent_date")
    val emailSentDate: LocalDateTime? = null,

)

fun MembershipFeeEntity.toDomain(): MembershipFee =
    MembershipFee(
        id = MembershipFeeId(this.id),
        userDetails = this.user.entityToDomain(),
        paymentDate = this.paymentDate,
        validUntil = this.validUntil,
        forMonth = MembershipFeeForMonthAndYear(this.forMonth),
        membershipFeeAmount = this.membershipFeeAmount.toDomain(),
        isPaid = MembershipFeeIsPaid(this.isPaid),
        isEmailSent = MembershipFeeEmailSent(this.isEmailSent),
        emailSentDate = this.emailSentDate,
    )
