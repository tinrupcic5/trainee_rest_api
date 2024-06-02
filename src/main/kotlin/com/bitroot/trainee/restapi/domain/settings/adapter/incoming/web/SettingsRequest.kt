package com.bitroot.trainee.restapi.domain.settings.adapter.incoming.web

data class SettingsRequest(
    val id: Long,
    val userId: Long,
    val language: String,
)
