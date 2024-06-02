package com.bitroot.trainee.restapi.domain.qrcode.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCode
import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCodeId
import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCodeText
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsEntity
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.entityToDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "qr_code")
data class QrCodeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_details_id", referencedColumnName = "id")
    val userDetails: UserDetailsEntity,

    @Column(name = "qr_code", nullable = false)
    val qrCode: String,

    @Column(name = "created")
    val created: LocalDateTime,
)

fun QrCodeEntity.toDomain(): QrCode =
    QrCode(
        id = QrCodeId(this.id!!),
        userDetails = this.userDetails.entityToDomain(),
        qrCode = QrCodeText(this.qrCode),
        created = this.created,

    )
