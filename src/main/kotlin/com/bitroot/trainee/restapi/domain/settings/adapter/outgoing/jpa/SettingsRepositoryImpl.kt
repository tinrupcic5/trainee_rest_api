package com.bitroot.trainee.restapi.domain.settings.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Settings
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.toEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class SettingsRepositoryImpl(
    val settingsEntityJpaRepository: SettingsEntityJpaRepository,
) : SettingsRepository {

    @Modifying
    override fun saveSettings(settings: Settings): Settings =
        settingsEntityJpaRepository.save(settings.toEntity()).toDomain()

    override fun getSettingsByUserDetailsId(userDetailsId: Long): Settings =
        settingsEntityJpaRepository.getSettingsByUserDetailsId(userDetailsId).toDomain()

    override fun getAllSettingsWhereRegistrationEmailWasNotSent(): List<Settings> =
        settingsEntityJpaRepository.getAllSettingsWhereRegistrationEmailWasNotSent().map { it.toDomain() }
}
