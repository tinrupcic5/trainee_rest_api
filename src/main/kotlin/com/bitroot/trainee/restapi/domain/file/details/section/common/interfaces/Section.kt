package com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces

import com.bitroot.trainee.restapi.domain.file.details.section.adapter.incoming.web.SectionRequest
import com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.jpa.SectionEntity
import com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.web.SectionDto
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.domainToEntity
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToDto
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.domainToEntity
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.toRequest
import org.springframework.core.io.Resource

data class Section(
    val id: SectionId?,
    val name: SectionName,
    val userDetails: UserDetails,
)

fun Section.toEntity(): SectionEntity =
    SectionEntity(
        id = this.id?.value,
        name = this.name.value,
        userDetails = this.userDetails.domainToEntity(),
    )

fun Section.toDto(resource: Resource? = null): SectionDto =
    SectionDto(
        id = this.id?.value,
        name = this.name.value,
        userDetails = this.userDetails.domainToDto(),
        resource = resource,
    )

fun Section.toRequest(): SectionRequest =
    SectionRequest(
        id = this.id?.value,
        name = this.name.value,
        userDetails = this.userDetails.toRequest(),
    )
