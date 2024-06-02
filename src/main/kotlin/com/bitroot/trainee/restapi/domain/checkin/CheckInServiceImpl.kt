package com.bitroot.trainee.restapi.domain.checkin

import com.bitroot.trainee.restapi.domain.checkin.adapter.incoming.web.CheckInRequest
import com.bitroot.trainee.restapi.domain.checkin.adapter.outgoing.jpa.CheckInRepository
import com.bitroot.trainee.restapi.domain.checkin.common.interfaces.CheckIn
import com.bitroot.trainee.restapi.domain.qrcode.QrCodeService
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class CheckInServiceImpl(
    val checkInRepository: CheckInRepository,
    val qrCodeService: QrCodeService,
) : CheckInService {
    override fun save(checkInRequest: CheckInRequest): String {
        val qrCode = qrCodeService.getQrCodeByQrCode(checkInRequest.qrCode)
        return if (qrCode == null) {
            "QR Code does not exist."
        } else {
            checkInRepository.save(
                CheckIn(
                    qrCode = qrCode,
                ),
            )
        }
    }
}
