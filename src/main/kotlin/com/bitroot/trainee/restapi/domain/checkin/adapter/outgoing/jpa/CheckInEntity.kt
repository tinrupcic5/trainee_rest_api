package com.bitroot.trainee.restapi.domain.checkin.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.checkin.common.interfaces.CheckIn
import com.bitroot.trainee.restapi.domain.checkin.common.interfaces.CheckInDate
import com.bitroot.trainee.restapi.domain.checkin.common.interfaces.CheckInId
import com.bitroot.trainee.restapi.domain.checkin.common.interfaces.CheckInTime
import com.bitroot.trainee.restapi.domain.qrcode.adapter.outgoing.jpa.QrCodeEntity
import com.bitroot.trainee.restapi.domain.qrcode.adapter.outgoing.jpa.toDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.sql.Time
import java.time.LocalDate

@Entity
@Table(name = "check_in")
data class CheckInEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "qr_code_id", referencedColumnName = "id", nullable = false)
    val qrCode: QrCodeEntity,

    @Column(name = "check_in_time", nullable = false)
    val checkInTime: Time,

    @Column(name = "check_in_date", nullable = false)
    val checkInDate: LocalDate,
)

fun CheckInEntity.toDomain(): CheckIn =
    CheckIn(
        id = CheckInId(this.id),
        qrCode = this.qrCode.toDomain(),
        checkInTime = CheckInTime(this.checkInTime),
        checkInDate = CheckInDate(this.checkInDate),
    )
