package com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.Section

interface SectionRepository {
    fun save(section: Section): String

    fun delete(section: Section): String

    fun getSectionByName(
        sectionName: String,
        sectionId: Long,
    ): Section

    fun getSectionById(sectionId: Long): Section?

    fun getAllSections(userId: Long): Set<Section>

    fun renameSection(
        sectionId: Long,
        oldSectionName: String,
        newSectionName: String,
    ): String
}
