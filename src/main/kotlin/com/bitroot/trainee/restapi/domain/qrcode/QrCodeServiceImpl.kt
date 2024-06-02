package com.bitroot.trainee.restapi.domain.qrcode

import com.bitroot.trainee.restapi.domain.qrcode.adapter.outgoing.jpa.QrCodeRepository
import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCode
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserId
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsRepository
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class QrCodeServiceImpl(
    val qrCodeRepository: QrCodeRepository,
    val userDetailsRepository: UserDetailsRepository,
) : QrCodeService {
    override fun saveQrCode(qrCode: QrCode): String =
        qrCodeRepository.saveQrCode(qrCode)

    override fun deleteQrCode(qrCode: QrCode): String =
        qrCodeRepository.deleteQrCode(qrCode)

    override fun getQrCodeForUser(userId: UserId): QrCode {
        val userDetails = userDetailsRepository.getUserDetailsByUserId(userId.value!!)
        return qrCodeRepository.getQrCodeForUserDetails(userDetails)
    }

    override fun getQrCodeByQrCode(qrCode: String): QrCode? =
        qrCodeRepository.getQrCodeByQrCode(qrCode)
}
