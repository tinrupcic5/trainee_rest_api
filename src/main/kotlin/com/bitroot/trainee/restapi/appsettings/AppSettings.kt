package com.bitroot.trainee.restapi.appsettings

import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.web.multipart.MultipartFile
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.Date

class AppSettings {
    companion object {
        const val PASSWORD_RESET_KEY: String = "Password Reset Key"
        const val TEMPORARY_PASSWORD_EMAIL_SUBJECT_HR: String = "Privremena registracijska lozinka"
        const val TEMPORARY_PASSWORD_EMAIL_SUBJECT_EN: String = "Temporary registration password"
        const val UTF_8: String = "UTF-8"
        const val VIDEO: String = "VIDEO"
        const val IMAGE: String = "IMAGE"
        const val RANDOM: String = "No-"
        const val NOTIFICATION: String = "NOTIFICATION"
        const val EMAIL_REGISTRATION_SUBJECT_HR: String = "Dobrodošli u Trainee!"
        const val EMAIL_REGISTRATION_SUBJECT_EN: String = "Welcome to Trainee!"
        const val HEADERS_NAME: String = "Content-Disposition"

        fun headersValue(name: String): String = "inline; filename=$name.png"

        fun contentId(
            name: String,
            id: Long,
        ): String = "${name}_$id"
    }
}

fun String.toLocalDateTime(): LocalDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)

fun Int.toMonthName(): String = "${Month.of(this)} ($this)"

fun LocalDateTime.toYearNowOrLastYear(): Int =
    if (this.monthValue == 1) {
        this.year - 1
    } else {
        this.year
    }

fun String.stringToDate1(): Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(this)

fun String.stringToDate() = SimpleDateFormat("yyyy-MM-dd").parse(this)

fun String.stringToEuropeanDate(): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val localDateTime = LocalDateTime.parse(this, formatter)
    val date = Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant())

    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
    return dateFormat.format(date)
}

fun isVideo(file: MultipartFile): Boolean = file.contentType?.startsWith("video/") == true

fun isImage(file: MultipartFile): Boolean = file.contentType?.startsWith("image/") == true

fun String.toMessageBody(): MessageBody = MessageBody(message = this)
