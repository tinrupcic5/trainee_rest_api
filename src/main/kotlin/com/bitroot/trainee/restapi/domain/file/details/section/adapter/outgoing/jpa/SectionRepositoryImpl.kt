package com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.Section
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.SectionName
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.toEntity
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class SectionRepositoryImpl(
    val sectionEntityJpaRepository: SectionEntityJpaRepository,
) : SectionRepository {
    override fun save(section: Section): String {
        sectionEntityJpaRepository.save(section.toEntity()).toDomain()
        return "Section saved"
    }

    override fun delete(section: Section): String {
        sectionEntityJpaRepository.delete(section.toEntity())
        return "Section deleted"
    }

    override fun getSectionByName(sectionName: String, sectionId: Long): Section =
        sectionEntityJpaRepository.getSectionByName(sectionName, sectionId).toDomain()

    override fun getSectionById(sectionId: Long): Section =
        sectionEntityJpaRepository.getSectionById(sectionId).toDomain()

    override fun getAllSections(userId: Long): Set<Section> =
        sectionEntityJpaRepository.getAllSections(userId).map { it.toDomain() }.toSet()

    override fun renameSection(sectionId: Long, oldSectionName: String, newSectionName: String): String {
        val oldSection = getSectionByName(oldSectionName, sectionId)
        save(oldSection.copy(name = SectionName(newSectionName)))

        return "Section renamed"
    }
}
