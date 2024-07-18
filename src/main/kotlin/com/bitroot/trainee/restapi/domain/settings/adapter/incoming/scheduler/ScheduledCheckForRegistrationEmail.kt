package com.bitroot.trainee.restapi.domain.settings.adapter.incoming.scheduler

import com.bitroot.trainee.restapi.domain.settings.SettingsService
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.sql.Time
import java.time.LocalTime

@Component
class ScheduledCheckForRegistrationEmail(
    private val settingsService: SettingsService,
) {
    private val logger = KotlinLogging.logger { }

    @Scheduled(
        cron = "\${trainee.jobs.registration-check.cron}",
        zone = "\${trainee.jobs.registration-check.time-zone}",
    )
    fun checkForRegistrationEmail() {
        LoggingContext.put(LoggingContext.UseCase.CHECK_FOR_REGISTRATION_EMAIL)
        settingsService.getAllSettingsWhereRegistrationEmailWasNotSent()
        logger.info("Check for registration email Time: @${Time.valueOf(LocalTime.now())}")
    }
}
