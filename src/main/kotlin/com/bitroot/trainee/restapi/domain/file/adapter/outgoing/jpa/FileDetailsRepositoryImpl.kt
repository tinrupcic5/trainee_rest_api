package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetails
import com.bitroot.trainee.restapi.domain.file.common.interfaces.toEntity
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.Section
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class FileDetailsRepositoryImpl(
    val fileDetailsEntityJpaRepository: FileDetailsEntityJpaRepository,
) : FileDetailsRepository {
    @Modifying
    override fun save(fileDetails: FileDetails): String {
        fileDetailsEntityJpaRepository.save(fileDetails.toEntity())

        return "File details saved"
    }

    override fun delete(fileDetails: FileDetails): String {
        fileDetailsEntityJpaRepository.delete(fileDetails.toEntity())
        return "File details deleted"
    }

    override fun getFileDetailsById(id: Long): FileDetails =
        fileDetailsEntityJpaRepository.getFileDetailsById(id).toDomain()

    override fun getFileDetailsByName(name: String): FileDetails =
        fileDetailsEntityJpaRepository.getFileDetailsByName(name).toDomain()

    override fun getFileDetailsBySectionId(sections: Set<Section>): List<FileDetails> {
        val sectionIds: Set<Long> = sections.map { it.id?.value!! }.toSet()
        val fileDetailsEntities = fileDetailsEntityJpaRepository.getFileDetailsBySectionId(sectionIds)

        return fileDetailsEntities.map { it.toDomain() }
    }

    override fun getAllFilesForSchool(userId: Long): Set<FileDetails> =
        fileDetailsEntityJpaRepository.getAllFilesForSchool(userId).map { it.toDomain() }.toSet()

    override fun renameFile(oldFileName: String, newFileName: String): String {
        val oldFile = fileDetailsEntityJpaRepository.getFileDetailsByName(oldFileName)

        fileDetailsEntityJpaRepository.save(oldFile.copy(name = newFileName))

        return "File renamed"
    }
}
