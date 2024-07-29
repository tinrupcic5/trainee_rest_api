package com.bitroot.trainee.restapi.domain.file.details.section

import com.bitroot.trainee.restapi.domain.file.details.section.adapter.incoming.web.SectionRequest
import com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.jpa.SectionRepository
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.Section
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.SectionId
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.SectionName
import com.bitroot.trainee.restapi.domain.file.properties.FileProperties
import com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web.requestToDomain
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class SectionServiceImpl(
    val sectionRepository: SectionRepository,
    val fileProperties: FileProperties,
) : SectionService {
    override fun save(sectionRequest: SectionRequest): String =
        sectionRepository.save(
            section =
                Section(
                    id = SectionId(sectionRequest.id),
                    name = SectionName(sectionRequest.name),
                    userDetails = sectionRequest.userDetails.requestToDomain(),
                ),
        )

    override fun delete(sectionId: Long): String {
        val section = sectionRepository.getSectionById(sectionId)
        sectionRepository.delete(section!!)
        fileProperties.deleteFolder(
            schoolName = section.userDetails.schoolDetails.school.schoolName.value,
            sectionName = section.name.value,
        )
        return "Section deleted"
    }

    override fun renameSection(
        sectionId: Long,
        oldSectionName: String,
        newSectionName: String,
    ): String = sectionRepository.renameSection(sectionId, oldSectionName, newSectionName)
}
