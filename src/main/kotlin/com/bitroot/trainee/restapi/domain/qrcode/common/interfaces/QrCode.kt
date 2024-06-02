package com.bitroot.trainee.restapi.domain.qrcode.common.interfaces

import com.bitroot.trainee.restapi.domain.qrcode.adapter.outgoing.jpa.QrCodeEntity
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToEntity
import java.time.LocalDateTime

data class QrCode(
    val id: QrCodeId? = null,
    val userDetails: UserDetails,
    val qrCode: QrCodeText? = null,
    val created: LocalDateTime? = null,
)

fun QrCode.toEntity(): QrCodeEntity =
    QrCodeEntity(
        id = this.id?.value,
        userDetails = this.userDetails.domainToEntity(),
        qrCode = this.qrCode!!.value,
        created = this.created!!,
    )
