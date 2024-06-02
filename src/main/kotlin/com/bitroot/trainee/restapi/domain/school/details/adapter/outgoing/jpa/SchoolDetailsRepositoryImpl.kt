package com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class SchoolDetailsRepositoryImpl(
    val schoolDetailsEntityJpaRepository: SchoolDetailsEntityJpaRepository,
) : SchoolDetailsRepository {
    override fun getSchoolDetailsById(schoolDetailsId: Long): SchoolDetails =
        schoolDetailsEntityJpaRepository.getSchoolDetailsById(schoolDetailsId).toDomain()
}
