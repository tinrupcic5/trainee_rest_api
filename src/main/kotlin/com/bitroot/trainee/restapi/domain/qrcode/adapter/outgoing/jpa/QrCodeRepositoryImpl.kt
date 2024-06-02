package com.bitroot.trainee.restapi.domain.qrcode.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCode
import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.toEntity
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class QrCodeRepositoryImpl(
    val qrCodeEntityJpaRepository: QrCodeEntityJpaRepository,
) : QrCodeRepository {
    private val logger = KotlinLogging.logger { }

    @Modifying
    override fun saveQrCode(qrCode: QrCode): String {
        val save = qrCodeEntityJpaRepository.save(qrCode.toEntity())
        logger.info("QrCode saved for ${save.userDetails.name} ${save.userDetails.lastName}.")
        return "QrCode saved for ${save.userDetails.name} ${save.userDetails.lastName}."
    }

    override fun deleteQrCode(qrCode: QrCode): String {
        qrCodeEntityJpaRepository.delete(qrCode.toEntity())
        return "QR Code deleted."
    }

    override fun getQrCodeForUserDetails(userDetails: UserDetails): QrCode =
        qrCodeEntityJpaRepository.getQrCodeForUserDetailsId(userDetails.id!!.value)!!.toDomain()

    override fun getQrCodeByQrCode(qrCode: String): QrCode? =
        qrCodeEntityJpaRepository.getQrCodeByQrCode(qrCode)?.toDomain()
}
