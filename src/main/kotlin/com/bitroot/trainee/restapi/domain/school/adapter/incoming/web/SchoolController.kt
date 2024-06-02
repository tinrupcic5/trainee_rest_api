package com.bitroot.trainee.restapi.domain.school.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.school.SchoolService
import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.web.SchoolDto
import com.bitroot.trainee.restapi.domain.school.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/school")
class SchoolController(
    val schoolService: SchoolService,
) {

    @GetMapping
    fun getAllSchool(): ResponseEntity<SchoolDto?> =
        ResponseEntity.status(HttpStatus.OK).body(
            schoolService.getAllSchool()?.domainToDto(),
        )

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<SchoolDto?> =
        ResponseEntity.status(HttpStatus.OK).body(
            schoolService.getSchoolById(id)?.domainToDto(),
        )

    @PostMapping
    fun saveOrUpdate(@RequestBody school: SchoolRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK).body(MessageBody(schoolService.saveOrUpdate(school.requestToDomain())))
}
