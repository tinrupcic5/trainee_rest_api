package com.bitroot.trainee.restapi.domain.settings.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Settings

interface SettingsRepository {
    fun saveSettings(settings: Settings): Settings
    fun getSettingsByUserDetailsId(userDetailsId: Long): Settings
    fun getAllSettingsWhereRegistrationEmailWasNotSent(): List<Settings>
}
