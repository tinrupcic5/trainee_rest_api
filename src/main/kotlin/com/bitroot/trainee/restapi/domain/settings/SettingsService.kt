package com.bitroot.trainee.restapi.domain.settings

import com.bitroot.trainee.restapi.domain.settings.adapter.incoming.web.SettingsRequest
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Settings

interface SettingsService {

    fun saveSettings(settings: SettingsRequest): String
    fun getSettingsByUserDetailsId(userDetailsId: Long): Settings
    fun getAllSettingsWhereRegistrationEmailWasNotSent(): List<Settings>
}
