package com.bitroot.trainee.restapi.domain.settings.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.settings.SettingsService
import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/settings")
class SettingsController(
    val settingsService: SettingsService,
) {

    @PutMapping
    fun updateSettingsForUser(@RequestBody settingsRequest: SettingsRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK).body(settingsService.saveSettings(settingsRequest).toMessageBody())
}
