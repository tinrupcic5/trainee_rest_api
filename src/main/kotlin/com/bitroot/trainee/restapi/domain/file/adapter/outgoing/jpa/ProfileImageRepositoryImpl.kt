package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImage
import com.bitroot.trainee.restapi.domain.file.common.interfaces.toEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class ProfileImageRepositoryImpl(
    val profileImageEntityJpaRepository: ProfileImageEntityJpaRepository,
) : ProfileImageRepository {
    @Modifying
    override fun save(profileImage: ProfileImage): String {
        profileImageEntityJpaRepository.save(profileImage.toEntity())

        return "Profile image saved"
    }

    override fun delete(profileImage: ProfileImage): String {
        profileImageEntityJpaRepository.delete(profileImage.toEntity())

        return "Profile image deleted"
    }

    override fun getProfileImageBySchoolId(schoolId: Long): ProfileImage =
        profileImageEntityJpaRepository.getProfileImageBySchoolId(schoolId).toDomain()

    override fun getProfileImageById(id: Long): ProfileImage =
        profileImageEntityJpaRepository.getReferenceById(id).toDomain()

    override fun getProfileImageByName(name: String): ProfileImage =
        profileImageEntityJpaRepository.getProfileImageByName(name).toDomain()
}
