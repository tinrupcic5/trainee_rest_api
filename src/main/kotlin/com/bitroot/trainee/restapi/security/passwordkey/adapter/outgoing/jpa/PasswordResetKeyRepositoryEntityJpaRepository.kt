package com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface PasswordResetKeyRepositoryEntityJpaRepository : JpaRepository<PasswordResetKeyEntity, Long> {

    @Modifying
    @Query(
        """
    UPDATE password_reset_keys
    SET is_used = :isUsed
    WHERE user_id = :userId
    """,
        nativeQuery = true,
    )
    fun updatePasswordResetKeyIsUsed(userId: Long, isUsed: Boolean): Int

    @Query(
        """
            SELECT 
              CASE 
                WHEN user_id IS NULL THEN 'User Not Found'
                WHEN reset_key <> :key THEN 'Wrong Reset Key'
                WHEN is_used = TRUE THEN 'Reset Key is Already Used'
                WHEN expiration <= CURRENT_TIMESTAMP THEN 'Reset Key is Expired'
                ELSE 'Reset Key is Valid'
              END AS reset_status
            FROM password_reset_keys
            WHERE user_id = :userId
    """,
        nativeQuery = true,
    )
    fun checkIfKeyIsValidForUser(userId: Long, key: String): String

    @Query(
        """
            SELECT * FROM password_reset_keys
            WHERE user_id = :userId
    """,
        nativeQuery = true,
    )
    fun getPasswordKeyByUserId(userId: Long): PasswordResetKeyEntity?
}
