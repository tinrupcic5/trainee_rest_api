package com.bitroot.trainee.restapi.domain.qrcode

import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCode
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserId

interface QrCodeService {
    fun saveQrCode(qrCode: QrCode): String
    fun deleteQrCode(qrCode: QrCode): String
    fun getQrCodeForUser(userId: UserId): QrCode
    fun getQrCodeByQrCode(qrCode: String): QrCode?
}
