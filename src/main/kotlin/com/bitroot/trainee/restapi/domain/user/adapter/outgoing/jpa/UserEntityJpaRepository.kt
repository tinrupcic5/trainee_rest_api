package com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface UserEntityJpaRepository : JpaRepository<UserEntity, Long> {

    @Query(
        """
        SELECT * FROM users WHERE user_name =:username AND password =:password
    """,
        nativeQuery = true,
    )
    fun login(username: String, password: String): Optional<UserEntity>

    @Query(
        """
        SELECT * FROM users WHERE user_name =:username
    """,
        nativeQuery = true,
    )
    fun getUserByUsername(username: String): Optional<UserEntity>

    @Modifying
    @Query(
        """
            UPDATE users
            SET password = :password
            WHERE id = :id
        """,
        nativeQuery = true,
    )
    fun updateUserPassword(id: Long, password: String): Int

    @Query(
        """
            SELECT u.*
                FROM users u
                JOIN user_details ud ON u.id = ud.user_id
                JOIN settings s ON ud.id = s.user_details_id
                WHERE s.is_registration_email_sent = TRUE;
        """,
        nativeQuery = true,
    )
    fun getAllUsers(): List<UserEntity>?
}
