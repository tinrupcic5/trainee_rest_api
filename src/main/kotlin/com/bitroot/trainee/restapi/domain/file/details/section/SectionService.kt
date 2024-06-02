package com.bitroot.trainee.restapi.domain.file.details.section

import com.bitroot.trainee.restapi.domain.file.details.section.adapter.incoming.web.SectionRequest

interface SectionService {

    fun save(sectionRequest: SectionRequest): String
    fun delete(sectionId: Long): String
    fun renameSection(sectionId: Long, oldSectionName: String, newSectionName: String): String
}
