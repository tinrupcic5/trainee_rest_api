package com.bitroot.trainee.restapi.domain.qrcode.properties

import com.bitroot.trainee.restapi.domain.qrcode.QrCodeService
import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCode
import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCodeText
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.*

@Service
class QrCodeGenerator(
    private val qrCodeService: QrCodeService,
) : QrCodeGeneratorService {
    @Value("\${qrcode.generator.location}")
    private val filePath = ""

    private val logger = KotlinLogging.logger { }

    override fun generateQRCodeImage(qrCode: QrCode): String {
        try {
            val qrCodeWriter = QRCodeWriter()
            val qrCodeNameFilePath =
                "${filePath}${qrCode.userDetails.name.value}_${qrCode.userDetails.lastName.value}_${qrCode.userDetails.user.id?.value}-QRCODE.png"
            val qrText = getQrText(
                qrCodeId = qrCode.userDetails.user.id!!.value.toString(),
                userDetailsFirstName = qrCode.userDetails.name.value,
                userDetailsLastName = qrCode.userDetails.lastName.value,
                userDetailsEmail = qrCode.userDetails.email.value.toString(),
                userDetailsPhoneNumber = qrCode.userDetails.phoneNumber?.value.toString(),
                schoolName = qrCode.userDetails.schoolDetails.school.schoolName.value,
                schoolLocation = qrCode.userDetails.schoolDetails.schoolLocation.value,
            )
            val bitMatrix = qrCodeWriter.encode(
                qrText,
                BarcodeFormat.QR_CODE,
                200,
                200,
            )
            qrCodeService.saveQrCode(
                qrCode.copy(
                    qrCode = QrCodeText(
                        qrText,
                    ),
                    created = LocalDateTime.now(),
                ),
            )

            val path: Path = FileSystems.getDefault().getPath(qrCodeNameFilePath)
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path)

            return "QrCode created."
        } catch (e: Exception) {
            logger.error("error: {}", e.stackTrace)
            return "QrCode not created."
        }
    }

    @Throws(WriterException::class, IOException::class)
    override fun getQRCodeImage(text: String): ByteArray {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200)

        val pngOutputStream = ByteArrayOutputStream()
        val con = MatrixToImageConfig(0xFF000002.toInt(), 0xFFFFC041.toInt())

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con)
        return pngOutputStream.toByteArray()
    }

    override fun deleteQRCodeImage(qrCode: QrCode): String {
        val qrCodeNameFilePath =
            "${filePath}${qrCode.userDetails.name.value}_${qrCode.userDetails.lastName.value}_${qrCode.userDetails.user.id?.value}-QRCODE.png"
        return try {
            val path: Path = FileSystems.getDefault().getPath(qrCodeNameFilePath)
            Files.deleteIfExists(path)
            "QrCode deleted for : ${qrCode.userDetails.name.value} ${qrCode.userDetails.lastName.value}"
        } catch (e: IOException) {
            logger.error("Error deleting QR code file: ${e.message}")
            "${e.message}"
        }
    }
}

fun getQrText(
    qrCodeId: String,
    userDetailsFirstName: String,
    userDetailsLastName: String,
    userDetailsEmail: String,
    userDetailsPhoneNumber: String,
    schoolName: String,
    schoolLocation: String,
): String {
    val concatenatedString =
        hashString(qrCodeId) +
            hashString(userDetailsFirstName) +
            hashString(userDetailsLastName) +
            hashString(userDetailsEmail) +
            hashString(userDetailsPhoneNumber) +
            hashString(schoolName) +
            hashString(schoolLocation) +
            "${LocalDateTime.now()}"

    return concatenatedString
}

private fun hashString(input: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
    return Base64.getEncoder().encodeToString(bytes)
}
