package com.bitroot.trainee.restapi.domain.settings

import com.bitroot.trainee.restapi.domain.settings.adapter.incoming.web.SettingsRequest
import com.bitroot.trainee.restapi.domain.settings.adapter.outgoing.jpa.SettingsRepository
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Language
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Settings
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsRepository
import com.bitroot.trainee.restapi.email.EmailService
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.EmailSentResult
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import java.sql.Time
import java.time.LocalTime

@Service
@Validated
class SettingsServiceImpl(
    val settingsRepository: SettingsRepository,
    val userDetailsRepository: UserDetailsRepository,
    val emailService: EmailService,
) : SettingsService {
    private val logger = KotlinLogging.logger { }

    override fun saveSettings(settings: SettingsRequest): String {
        val userDetails = userDetailsRepository.getUserDetailsByUserId(settings.userId)

        val settingsValue = settingsRepository.getSettingsByUserDetailsId(userDetails.id!!.value)
        settingsRepository.saveSettings(
            settingsValue.copy(
                userDetails = userDetails,
                language = Language(settings.language),
            ),
        )
        return "Settings saved."
    }

    override fun getSettingsByUserDetailsId(userDetailsId: Long): Settings =
        settingsRepository.getSettingsByUserDetailsId(userDetailsId)

    override fun getAllSettingsWhereRegistrationEmailWasNotSent(): List<Settings> {
        val list = settingsRepository.getAllSettingsWhereRegistrationEmailWasNotSent()
        list.forEach {
            val settingUser = settingsRepository.getSettingsByUserDetailsId(it.userDetails.id!!.value)
            return when (
                emailService.sendSuccessfullRegistrationEmailMessage(
                    to = it.userDetails.email.value!!,
                    userDetails = it.userDetails,
                    schoolName = it.userDetails.schoolDetails.school.schoolName.value,
                    languageSettings = settingUser.language,
                )
            ) {
                is EmailSentResult.Failed -> emptyList()
                is EmailSentResult.Success -> list.let {
                    val setings = settingsRepository.getSettingsByUserDetailsId(settingUser.userDetails.id!!.value)
                    settingsRepository.saveSettings(setings.copy(isRegistrationEmailSent = true))
                    logger.info("Registration email sent to: ${setings.userDetails.name.value} ${setings.userDetails.lastName.value} @${Time.valueOf(LocalTime.now())}")
                    list
                }
            }
        }

        return list
    }
}
