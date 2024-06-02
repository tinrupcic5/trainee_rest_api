package com.bitroot.trainee.restapi.email

import com.bitroot.trainee.restapi.appsettings.AppSettings
import com.bitroot.trainee.restapi.appsettings.AppSettings.Companion.contentId
import com.bitroot.trainee.restapi.appsettings.sendEmailMessageWithResetKey
import com.bitroot.trainee.restapi.appsettings.sendPaymentWarningMessageEn
import com.bitroot.trainee.restapi.appsettings.sendPaymentWarningMessageHr
import com.bitroot.trainee.restapi.appsettings.sendSuccessfullRegistrationEmailMessageEn
import com.bitroot.trainee.restapi.appsettings.sendSuccessfullRegistrationEmailMessageHr
import com.bitroot.trainee.restapi.appsettings.sendTemporaryPasswordEmailMessageEn
import com.bitroot.trainee.restapi.appsettings.sendTemporaryPasswordEmailMessageHr
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFee
import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCode
import com.bitroot.trainee.restapi.domain.qrcode.properties.QrCodeGeneratorService
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Language
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.errorhandling.EmailSendExcaption
import com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.web.PasswordResetKeyEmailDto
import com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.web.TemporaryPasswordEmailDto
import com.bitroot.trainee.restapi.security.passwordkey.localTimeToString
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.EmailSentResult
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.PasswordResetKeyEmailResult
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.TemporaryPasswordEmailResult
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File

@Service
class EmailServiceImpl(
    private val javaMailSender: JavaMailSender,
    private val qrCodeGeneratorService: QrCodeGeneratorService,

) : EmailService {

    @Value("\${qrcode.generator.location}")
    private val location: String = ""

    private fun qrCodeName(userDetails: UserDetails) =
        "${location}${userDetails.name.value}_${userDetails.lastName.value}_${userDetails.user.id?.value}-QRCODE.png"

    override fun sendTemporaryPasswordToRegisteredUser(
        to: String,
        name: String,
        password: String,
        language: Language,
        schoolName: String,
    ): TemporaryPasswordEmailResult {
        try {
            val message = javaMailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)
            helper.setTo(to)
            if (language.value == LanguageEnum.HR.name) {
                helper.setSubject("$schoolName ${AppSettings.TEMPORARY_PASSWORD_EMAIL_SUBJECT_HR}")
                helper.setText(sendTemporaryPasswordEmailMessageHr(name, password, schoolName), true)
            } else {
                helper.setSubject("$schoolName ${AppSettings.TEMPORARY_PASSWORD_EMAIL_SUBJECT_EN}")
                helper.setText(sendTemporaryPasswordEmailMessageEn(name, password, schoolName), true)
            }

            javaMailSender.send(message)
            return TemporaryPasswordEmailResult.Success(
                TemporaryPasswordEmailDto(
                    message = "Email sent",
                    to = to,
                    name = name,
                    password = password,
                ),
            )
        } catch (e: Exception) {
            throw EmailSendExcaption(e.cause.toString())
        }
    }

    override fun sendKeyPasswordEmail(
        to: String,
        userDetails: UserDetails,
        key: String,
        expires: String,
    ): PasswordResetKeyEmailResult {
        try {
            val message = javaMailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)
            helper.setTo(to)
            helper.setSubject(AppSettings.PASSWORD_RESET_KEY)

            val f =
                FileSystemResource(File(qrCodeName(userDetails)))

            helper.setText(sendEmailMessageWithResetKey(userDetails.name.value, key, expires, f), true)
            helper.addInline(
                contentId(userDetails.name.value, userDetails.user.id!!.value!!),
                f,
                MediaType.IMAGE_PNG_VALUE,
            )
            message.setHeader(AppSettings.HEADERS_NAME, AppSettings.headersValue(userDetails.name.value))

            javaMailSender.send(message)
            return PasswordResetKeyEmailResult.Success(
                PasswordResetKeyEmailDto(
                    to = to,
                    name = userDetails.name.value,
                    key = key,
                    expires = expires,
                    message = "Email sent",
                ),
            )
        } catch (e: Exception) {
            throw EmailSendExcaption(e.message.toString())
        }
    }

    override fun sendWarningPaymentEmail(membershipFee: List<MembershipFee>): EmailSentResult {
        try {
            for (feeUsersList in membershipFee) {
                if (!feeUsersList.userDetails.email.value.isNullOrBlank()) {
                    val message = javaMailSender.createMimeMessage()
                    val helper = MimeMessageHelper(message, true)

                    helper.setTo(feeUsersList.userDetails.email.value)
                    if (feeUsersList.languageSettings?.value == LanguageEnum.HR.name) {
                        helper.setSubject("${feeUsersList.userDetails.schoolDetails.school.schoolName.value} Članarina")
                        helper.setText(
                            sendPaymentWarningMessageHr(
                                feeUsersList.userDetails.name.value,
                                feeUsersList.userDetails.schoolDetails.school.schoolName.value,
                                feeUsersList.paymentDate.localTimeToString(),
                                feeUsersList.validUntil.localTimeToString(),
                                feeUsersList.forMonth.value,
                            ),
                            true,
                        )
                    } else {
                        helper.setSubject("${feeUsersList.userDetails.schoolDetails.school.schoolName.value} Membership fee")
                        helper.setText(
                            sendPaymentWarningMessageEn(
                                feeUsersList.userDetails.name.value,
                                feeUsersList.userDetails.schoolDetails.school.schoolName.value,
                                feeUsersList.paymentDate.localTimeToString(),
                                feeUsersList.validUntil.localTimeToString(),
                                feeUsersList.forMonth.value,
                            ),
                            true,
                        )
                    }
                    javaMailSender.send(message)
                }
            }

            return EmailSentResult.Success("Email Sent")
        } catch (e: Exception) {
            throw EmailSendExcaption(e.cause.toString())
        }
    }

    override fun sendSuccessfullRegistrationEmailMessage(
        to: String,
        userDetails: UserDetails,
        schoolName: String,
        languageSettings: Language?,
    ): EmailSentResult {
        val f = FileSystemResource(File(qrCodeName(userDetails)))
        qrCodeGeneratorService.generateQRCodeImage(
            qrCode = QrCode(
                userDetails = userDetails,
            ),
        )
        try {
            val message = javaMailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true, AppSettings.UTF_8)
            helper.setTo(to)
            if (languageSettings?.value == LanguageEnum.HR.name) {
                helper.setSubject(AppSettings.EMAIL_REGISTRATION_SUBJECT_HR)
                helper.setText(sendSuccessfullRegistrationEmailMessageHr(userDetails.name.value, schoolName), true)
            } else {
                helper.setSubject(AppSettings.EMAIL_REGISTRATION_SUBJECT_EN)
                helper.setText(sendSuccessfullRegistrationEmailMessageEn(userDetails.name.value, schoolName), true)
            }
            helper.addInline("image", f)

            javaMailSender.send(message)
            return EmailSentResult.Success("Email Sent")
        } catch (e: Exception) {
            throw EmailSendExcaption(e.message.toString())
        }
    }
}
