package com.bitroot.trainee.restapi.domain.qrcode.adapter.incoming.scheduler

import com.bitroot.trainee.restapi.domain.qrcode.common.interfaces.QrCode
import com.bitroot.trainee.restapi.domain.qrcode.properties.QrCodeGeneratorService
import com.bitroot.trainee.restapi.domain.user.UserService
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsRepository
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.sql.Time
import java.time.LocalTime

@Component
class ScheduledDeleteQrCode(
    private val userService: UserService,
    private val userDetailsRepository: UserDetailsRepository,
    private val generatorService: QrCodeGeneratorService,
) {
    private val logger = KotlinLogging.logger { }

    @Scheduled(
        cron = "\${trainee.jobs.qrcode-delete.cron}",
        zone = "\${trainee.jobs.qrcode-delete.time-zone}",
    )
    fun deleteOldQrCodes() {
        LoggingContext.put(LoggingContext.UseCase.CHECK_FOR_REGISTRATION_EMAIL)
        val userList = userService.getAllUsers()
        userList?.forEach {
            val userDetails = userDetailsRepository.getUserDetailsByUserId(it.id!!)
            generatorService.deleteQRCodeImage(
                QrCode(
                    userDetails = userDetails,
                ),
            )
        }
        logger.info("Delete QR Codes from files, Time: @${Time.valueOf(LocalTime.now())}")
    }
}
