package com.bitroot.trainee.restapi.domain.school.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.school.common.interfaces.School
import com.bitroot.trainee.restapi.domain.school.common.interfaces.domainToEntity
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class SchoolRepositoryImpl(
    private val schoolEntityJpaRepository: SchoolEntityJpaRepository,
) : SchoolRepository {
    private val logger = KotlinLogging.logger { }

    override fun getAllSchool(): School? =
        schoolEntityJpaRepository.getAllSchool()?.entityToDomain()

    override fun getSchoolById(schoolId: Long): School? =
        schoolEntityJpaRepository.getReferenceById(schoolId).entityToDomain()

    @Modifying
    override fun saveOrUpdate(school: School): String {
        if (school.schoolId?.value != null) {
            val sch = schoolEntityJpaRepository.getReferenceById(school.schoolId.value)
            schoolEntityJpaRepository.save(sch.copy(schoolName = school.schoolName.value))
            return "School updated."
        } else {
            schoolEntityJpaRepository.save(school.domainToEntity())
            logger.info { "School with name : ${school.schoolName} saved." }
            return "School saved."
        }
    }
}
