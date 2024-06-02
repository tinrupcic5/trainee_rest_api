package com.bitroot.trainee.restapi.domain.settings.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Language
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Settings
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.SettingsId
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsEntity
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.entityToDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "settings")
data class SettingsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_details_id", referencedColumnName = "id")
    val userDetails: UserDetailsEntity,

    @Column(name = "language", nullable = false)
    val language: String,

    @Column(name = "is_registration_email_sent", nullable = false)
    val isRegistrationEmailSent: Boolean? = null,
)
fun SettingsEntity.toDomain(): Settings =
    Settings(
        id = SettingsId(this.id),
        userDetails = this.userDetails.entityToDomain(),
        language = Language(this.language),
        isRegistrationEmailSent = this.isRegistrationEmailSent,
    )
