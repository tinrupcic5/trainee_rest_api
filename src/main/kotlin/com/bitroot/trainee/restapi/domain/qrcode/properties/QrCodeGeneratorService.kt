package com.bitroot.trainee.restapi.domain.qrcode.properties

import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCode

interface QrCodeGeneratorService {
    fun generateQRCodeImage(qrCode: QrCode): String
    fun getQRCodeImage(text: String): ByteArray
    fun deleteQRCodeImage(qrCode: QrCode): String
}
