package com.bitroot.trainee.restapi.domain.qrcode.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface QrCodeEntityJpaRepository : JpaRepository<QrCodeEntity, Long> {

    @Query(
        """
            SELECT * FROM qr_code WHERE user_details_id = :userDetailsId
        """,
        nativeQuery = true,
    )
    fun getQrCodeForUserDetailsId(userDetailsId: Long): QrCodeEntity?

    @Query(
        """
            SELECT * FROM qr_code WHERE qr_code = :qrCode
        """,
        nativeQuery = true,
    )
    fun getQrCodeByQrCode(qrCode: String): QrCodeEntity?
}
