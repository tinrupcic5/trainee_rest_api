package com.bitroot.trainee.restapi.domain.membership.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.appsettings.stringToEuropeanDate
import com.bitroot.trainee.restapi.appsettings.toMonthName
import com.bitroot.trainee.restapi.appsettings.toYearNowOrLastYear
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFee
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFeeEmailSent
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.toEntity
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class MembershipFeeRepositoryImpl(
    val membershipFeeEntityJpaRepository: MembershipFeeEntityJpaRepository,
) : MembershipFeeRepository {
    override fun payMembership(membershipFee: MembershipFee): String {
        membershipFeeEntityJpaRepository.save(membershipFee.toEntity())
        return if (membershipFee.isPaid.value) {
            "${membershipFee.userDetails.name.value} has payed for Month ${membershipFee.forMonth.value.toMonthName()} on ${membershipFee.paymentDate.toString().stringToEuropeanDate()}."
        } else {
            "${membershipFee.userDetails.name.value} has not payed for Month ${membershipFee.forMonth.value.toMonthName()} ${membershipFee.validUntil.toYearNowOrLastYear()}."
        }
    }

    override fun membershipCheck(): List<MembershipFee> =
        membershipFeeEntityJpaRepository.membershipCheck().map { it.toDomain() }

    override fun membershipCheckForUnpaidFeeOverMonthAndTenDays(): List<MembershipFee> =
        membershipFeeEntityJpaRepository.membershipCheckForUnpaidFeeOverMonthAndTenDays().map { it.toDomain() }

    override fun saveMembershipFeeEmailList(emailList: List<MembershipFee>): String {
        emailList.forEach { membershipFee ->
            val updatedMembershipFee = membershipFee.copy(
                isEmailSent = MembershipFeeEmailSent(true),
                emailSentDate = LocalDateTime.now(),
            )
            membershipFeeEntityJpaRepository.save(updatedMembershipFee.toEntity())
        }

        return "Membership fees updated with email sent information."
    }

    override fun getAllMembershipBySchool(schoolId: Long): List<MembershipFee> =
        membershipFeeEntityJpaRepository.getAllMembershipBySchool(schoolId).map { it.toDomain() }

    override fun getMembershipById(membershipFeeId: Long): MembershipFee =
        membershipFeeEntityJpaRepository.getReferenceById(membershipFeeId).toDomain()

    override fun getAllMembershipForExactMonth(year: Int, month: Int, schoolId: Long): List<MembershipFee> =
        membershipFeeEntityJpaRepository.getAllMembershipForExactMonth(year, month, schoolId).map { it.toDomain() }

    override fun findByUserAndLastPaymentDate(userDetailsId: Long, isPaid: Boolean): MembershipFee? =
        membershipFeeEntityJpaRepository.findByUserAndLastPaymentDate(userDetailsId, isPaid)?.toDomain()

    override fun deletePayment(membershipFeeId: Long): String {
        membershipFeeEntityJpaRepository.delete(membershipFeeEntityJpaRepository.findById(membershipFeeId).get())
        return "Membership Fee deleted"
    }
}
