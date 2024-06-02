package com.bitroot.trainee.restapi.appsettings.adapter.incoming.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/version")
class VersionController {
    @Value("\${info.version}")
    private val version = ""

    @Value("\${info.deployed}")
    private val deployed = ""

    @Value("\${info.time}")
    private val time = ""

    private val textVersion = """
        version: $version
        deployed: $deployed
        time: $time
    """.trimIndent()

    @GetMapping
    fun getVersion(): ResponseEntity<String> =
        ResponseEntity.status(HttpStatus.OK).body(textVersion)
}
