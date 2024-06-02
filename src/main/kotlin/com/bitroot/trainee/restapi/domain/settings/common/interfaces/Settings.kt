package com.bitroot.trainee.restapi.domain.settings.common.interfaces

import com.bitroot.trainee.restapi.domain.settings.adapter.outgoing.jpa.SettingsEntity
import com.bitroot.trainee.restapi.domain.user.common.interfaces.domainToEntity
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToEntity

data class Settings(
    val id: SettingsId? = null,
    val userDetails: UserDetails,
    val language: Language,
    val isRegistrationEmailSent: Boolean? = null,
)

fun Settings.toEntity(): SettingsEntity =
    SettingsEntity(
        id = this.id?.value,
        userDetails = this.userDetails.domainToEntity(),
        language = this.language.value,
        isRegistrationEmailSent = this.isRegistrationEmailSent,
    )
