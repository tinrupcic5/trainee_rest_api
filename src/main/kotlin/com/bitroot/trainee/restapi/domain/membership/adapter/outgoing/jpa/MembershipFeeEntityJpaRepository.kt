package com.bitroot.trainee.restapi.domain.membership.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface MembershipFeeEntityJpaRepository : JpaRepository<MembershipFeeEntity, Long> {

    @Query(
        """
    SELECT mf.user_details_id,
       mf.id, 
       mf.is_paid, 
       mf.membership_fee_amount_id, 
       mf.user_details_id, 
       mf.payment_date, 
       mf.for_month, 
       mf.valid_until, 
       mfa.amount, 
       mfa.currency, 
       mf.is_email_sent, 
       mf.email_sent_date
        FROM membership_fee mf
        JOIN membership_fee_amount mfa ON mf.membership_fee_amount_id = mfa.id
        WHERE mf.is_paid = FALSE
        AND mf.valid_until < NOW() - INTERVAL '2 DAY'
        AND mf.is_email_sent = FALSE;
    """,
        nativeQuery = true,
    )
    fun membershipCheck(): List<MembershipFeeEntity>

    @Query(
        """
    SELECT mf.user_details_id,
       mf.id, 
       mf.is_paid, 
       mf.membership_fee_amount_id, 
       mf.user_details_id, 
       mf.payment_date, 
       mf.for_month, 
       mf.valid_until, 
       mfa.amount, 
       mfa.currency, 
       mf.is_email_sent, 
       mf.email_sent_date
        FROM membership_fee mf
        JOIN membership_fee_amount mfa ON mf.membership_fee_amount_id = mfa.id
        WHERE mf.is_paid = FALSE
        AND mf.valid_until < NOW() - INTERVAL '10 DAY'
        AND mf.is_email_sent = FALSE;
    """,
        nativeQuery = true,
    )
    fun membershipCheckForUnpaidFeeOverMonthAndTenDays(): List<MembershipFeeEntity>

    @Query(
        """
        SELECT mf.*
        FROM membership_fee mf
        JOIN user_details ud ON mf.user_details_id = ud.id
        JOIN school_details sd ON ud.school_details_id = sd.id
        WHERE sd.school_Id = :schoolId;
    """,
        nativeQuery = true,
    )
    fun getAllMembershipBySchool(schoolId: Long): List<MembershipFeeEntity>

    @Query(
        """
        SELECT mf.*
        FROM membership_fee mf
                 JOIN user_details ud ON mf.user_details_id = ud.id
                 JOIN school_details sd ON ud.school_details_id = sd.id
        WHERE EXTRACT(YEAR FROM mf.payment_date) = :year
          AND EXTRACT(MONTH FROM mf.payment_date) = :month
          AND sd.school_id = :schoolId
    """,
        nativeQuery = true,
    )
    fun getAllMembershipForExactMonth(year: Int, month: Int, schoolId: Long): List<MembershipFeeEntity>

    @Query(
        """
    SELECT * FROM membership_fee 
    WHERE user_details_id = :userDetailsId
    AND is_paid = :isPaid
    ORDER BY payment_date DESC
    LIMIT 1
    """,
        nativeQuery = true,
    )
    fun findByUserAndLastPaymentDate(userDetailsId: Long, isPaid: Boolean): MembershipFeeEntity?
}
