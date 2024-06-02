package com.bitroot.trainee.restapi.domain.qrcode.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCode
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails

interface QrCodeRepository {
    fun saveQrCode(qrCode: QrCode): String
    fun deleteQrCode(qrCode: QrCode): String
    fun getQrCodeForUserDetails(userDetails: UserDetails): QrCode
    fun getQrCodeByQrCode(qrCode: String): QrCode?
}
