package com.bitroot.trainee.restapi.domain.membership

import com.bitroot.trainee.restapi.appsettings.stringToEuropeanDate
import com.bitroot.trainee.restapi.appsettings.toLocalDateTime
import com.bitroot.trainee.restapi.domain.membership.adapter.incoming.web.MembershipFeeRequest
import com.bitroot.trainee.restapi.domain.membership.adapter.outgoing.jpa.MembershipFeeRepository
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFee
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFeeEmailSent
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFeeForMonthAndYear
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFeeIsPaid
import com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.jpa.MembershipFeeAmountRepository
import com.bitroot.trainee.restapi.domain.settings.adapter.outgoing.jpa.SettingsRepository
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsRepository
import com.bitroot.trainee.restapi.email.EmailService
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.EmailSentResult
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime

@Service
@Validated
class MembershipFeeServiceImpl(
    val membershipFeeRepository: MembershipFeeRepository,
    val membershipFeeAmountRepository: MembershipFeeAmountRepository,
    val emailService: EmailService,
    val settingsRepository: SettingsRepository,
    val userDetailsRepository: UserDetailsRepository,
) : MembershipFeeService {
    private val logger = KotlinLogging.logger { }

    /**
     * if its not payed, pay , then create a new payment for next month
     *if its canceled then just upsert the existing payment to not payed
     */
    override fun payMembership(membershipFee: MembershipFeeRequest): String {
        if (membershipFee.id == null) {
            logger.info("Pay Membership When User Pays For The First Time")
            return payMembershipWhenUserPaysForTheFirstTime(membershipFee)
        }
        if (!membershipFee.isPaid) {
            logger.info("Change membership from true to false if user has not payed")
            return payMembershipWhenUserWantsToPutFalseWhenItWasPaid(membershipFee)
        }
        if (membershipFee.isPaid) {
            logger.info("Change membership from false to true if user has payed")
            return payMembershipWhenUserWantsToPutItFromFalseToTrue(membershipFee)
        }
        return ""
    }

    fun payMembershipWhenUserPaysForTheFirstTime(membershipFeeRequest: MembershipFeeRequest): String {
        val userDetails = userDetailsRepository.getUserDetailsByUserId(membershipFeeRequest.userId)

        val membershipFeeAmount =
            membershipFeeAmountRepository.getAllAmountForSchoolDetailsId(userDetails.schoolDetails.id?.value!!)
        val membershipFee = MembershipFee(
            userDetails = userDetails,
            paymentDate = LocalDateTime.now(),
            forMonth = MembershipFeeForMonthAndYear(membershipFeeRequest.forMonthAndYear.toLocalDateTime().monthValue),
            membershipFeeAmount = membershipFeeAmount,
            isPaid = MembershipFeeIsPaid(value = true),
            isEmailSent = MembershipFeeEmailSent(value = false),
            validUntil = validUntil(membershipFeeRequest.forMonthAndYear.toLocalDateTime()),
        )

        val isPayed = membershipFeeRepository.payMembership(membershipFee)
        logger.info("{}", isPayed)

        return isPayed + " and it is valid until ${
            validUntil(membershipFeeRequest.forMonthAndYear.toLocalDateTime()).toString().stringToEuropeanDate()
        }"
    }

    private fun payMembershipWhenUserWantsToPutFalseWhenItWasPaid(membershipFee: MembershipFeeRequest): String {
        val existingPayment = membershipFeeRepository.getMembershipById(membershipFee.id!!)

        val thisPaymentMonth = existingPayment.copy(
            isPaid = MembershipFeeIsPaid(value = false),
        )
        val paid = membershipFeeRepository.payMembership(thisPaymentMonth)

        return paid
    }

    fun validUntil(forMonthAndYear: LocalDateTime): LocalDateTime {
        val today = LocalDateTime.now()

        // Set the day, month, and year
        val validUntil = forMonthAndYear
            .withDayOfMonth(today.dayOfMonth)
            .withMonth(forMonthAndYear.monthValue % 12 + 1)
            .withYear(if (forMonthAndYear.monthValue == 12) forMonthAndYear.year + 1 else forMonthAndYear.year)

        // Set the minutes and seconds to the current time
        val finalValidUntil = validUntil
            .withHour(today.hour)
            .withMinute(today.minute)
            .withSecond(today.second)
            .withNano(today.nano)

        return finalValidUntil
    }

    private fun payMembershipWhenUserWantsToPutItFromFalseToTrue(membershipFeeRequest: MembershipFeeRequest): String {
        val existingPayment = membershipFeeRepository.getMembershipById(membershipFeeRequest.id!!)

        val thisPaymentMonth = existingPayment.copy(
            paymentDate = LocalDateTime.now(),
            forMonth = MembershipFeeForMonthAndYear(membershipFeeRequest.forMonthAndYear.toLocalDateTime().monthValue),
            isPaid = MembershipFeeIsPaid(value = true),
            validUntil = validUntil(membershipFeeRequest.forMonthAndYear.toLocalDateTime()),
        )
        val payedNow = membershipFeeRepository.payMembership(thisPaymentMonth)

        logger.info("{}", payedNow)
        return payedNow + " and it is valid until ${
            validUntil(membershipFeeRequest.forMonthAndYear.toLocalDateTime()).toString().stringToEuropeanDate()
        }"
    }

    override fun getAllMembershipBySchool(schoolId: Long): List<MembershipFee> =
        membershipFeeRepository.getAllMembershipBySchool(schoolId)

    override fun membershipCheck(): String {
        val listOfMembershipFee = membershipFeeRepository.membershipCheck().map { membershipFee ->
            val settings = settingsRepository.getSettingsByUserDetailsId(membershipFee.userDetails.user.id?.value!!)
            membershipFee.copy(languageSettings = settings.language)
        }

        if (listOfMembershipFee.isNotEmpty()) {
            val email = emailService.sendWarningPaymentEmail(listOfMembershipFee)
            return when (email) {
                is EmailSentResult.Success -> membershipFeeRepository.saveMembershipFeeEmailList(listOfMembershipFee)
                is EmailSentResult.Failed -> "Mail failed to send."
            }
        }
        return "Nothing to send."
    }

    override fun membershipCheckForUnpaidFeeOverMonthAndTenDays(): List<MembershipFee> =
        membershipFeeRepository.membershipCheckForUnpaidFeeOverMonthAndTenDays()

    override fun getAllMembershipForExactMonth(year: Int, month: Int, schoolId: Long): List<MembershipFee> =
        membershipFeeRepository.getAllMembershipForExactMonth(year, month, schoolId)
}
