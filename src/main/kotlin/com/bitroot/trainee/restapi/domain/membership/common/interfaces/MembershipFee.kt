package com.bitroot.trainee.restapi.domain.membership.common.interfaces

import com.bitroot.trainee.restapi.domain.membership.adapter.outgoing.jpa.MembershipFeeEntity
import com.bitroot.trainee.restapi.domain.membership.adapter.outgoing.web.MembershipFeeDto
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.MembershipFeeAmount
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.toEntity
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Language
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToEntity
import com.bitroot.trainee.restapi.security.passwordkey.localTimeToString
import com.bitroot.trainee.restapi.security.passwordkey.localTimeToStringForMail
import java.time.LocalDateTime

data class MembershipFee(
    val id: MembershipFeeId? = null,
    val userDetails: UserDetails,
    val paymentDate: LocalDateTime,
    val validUntil: LocalDateTime,
    val forMonth: MembershipFeeForMonthAndYear,
    val membershipFeeAmount: MembershipFeeAmount,
    val isPaid: MembershipFeeIsPaid,
    val isEmailSent: MembershipFeeEmailSent,
    val emailSentDate: LocalDateTime? = null,
    val languageSettings: Language? = null,
)

fun MembershipFee.toDto(): MembershipFeeDto =
    MembershipFeeDto(
        id = this.id?.value,
        userDetails = this.userDetails.domainToDto(),
        paymentDate = this.paymentDate.localTimeToString(),
        validUntil = this.validUntil.localTimeToString(),
        forMonth = this.forMonth.value,
        membershipFeeAmount = this.membershipFeeAmount.toDto(),
        isPaid = this.isPaid.value,
        emailSentDate = this.emailSentDate?.localTimeToStringForMail(),
        isEmailSent = this.isEmailSent.value,
    )

fun MembershipFee.toEntity(): MembershipFeeEntity =
    MembershipFeeEntity(
        id = this.id?.value,
        user = this.userDetails.domainToEntity(),
        paymentDate = this.paymentDate,
        validUntil = this.validUntil,
        membershipFeeAmount = this.membershipFeeAmount.toEntity(),
        isPaid = this.isPaid.value,
        isEmailSent = this.isEmailSent.value,
        emailSentDate = this.emailSentDate,
        forMonth = this.forMonth.value,
    )
