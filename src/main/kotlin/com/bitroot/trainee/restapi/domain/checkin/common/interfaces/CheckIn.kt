package com.bitroot.trainee.restapi.domain.checkin.common.interfaces

import com.bitroot.trainee.restapi.domain.checkin.adapter.outgoing.jpa.CheckInEntity
import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCode
import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.toEntity
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime

data class CheckIn(
    val id: CheckInId? = null,
    val checkInDate: CheckInDate? = null,
    val checkInTime: CheckInTime? = null,
    val qrCode: QrCode,
)

fun CheckIn.toEntity(): CheckInEntity =
    CheckInEntity(
        id = this.id?.value,
        qrCode = this.qrCode.toEntity(),
        checkInTime = Time.valueOf(LocalTime.now()),
        checkInDate = LocalDate.now(),
    )
