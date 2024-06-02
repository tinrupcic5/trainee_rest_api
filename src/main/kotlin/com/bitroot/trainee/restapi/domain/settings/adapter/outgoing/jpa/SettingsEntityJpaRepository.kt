package com.bitroot.trainee.restapi.domain.settings.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface SettingsEntityJpaRepository : JpaRepository<SettingsEntity, Long> {

    @Query(
        """
        SELECT * FROM settings WHERE user_details_id =:userDetailsId
    """,
        nativeQuery = true,
    )
    fun getSettingsByUserDetailsId(userDetailsId: Long): SettingsEntity

    @Query(
        """
        SELECT * FROM settings
        WHERE is_registration_email_sent = false
    """,
        nativeQuery = true,
    )
    fun getAllSettingsWhereRegistrationEmailWasNotSent(): List<SettingsEntity>
}
