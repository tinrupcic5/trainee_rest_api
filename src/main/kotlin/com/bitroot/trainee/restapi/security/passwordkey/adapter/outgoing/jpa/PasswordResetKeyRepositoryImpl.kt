package com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.PasswordResetKey
import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.toEntity
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class PasswordResetKeyRepositoryImpl(
    private val passwordResetKeyRepositoryEntityJpaRepository: PasswordResetKeyRepositoryEntityJpaRepository,
) : PasswordResetKeyRepository {

    private val logger = KotlinLogging.logger { }

    override fun createPasswordKey(passwordResetKey: PasswordResetKey): PasswordResetKey =
        passwordResetKeyRepositoryEntityJpaRepository.save(
            passwordResetKey.toEntity(),
        ).toDomain()

    override fun getPasswordKeyByUserId(userId: Long): PasswordResetKey? =
        passwordResetKeyRepositoryEntityJpaRepository.getPasswordKeyByUserId(userId)?.toDomain()

    override fun updatePasswordResetKeyIsUsed(userId: Long, isUsed: Boolean): Boolean =
        passwordResetKeyRepositoryEntityJpaRepository.updatePasswordResetKeyIsUsed(userId, isUsed) == 1

    override fun checkIfKeyIsValidForUser(userId: Long, key: String): String =
        passwordResetKeyRepositoryEntityJpaRepository.checkIfKeyIsValidForUser(userId, key)
}
