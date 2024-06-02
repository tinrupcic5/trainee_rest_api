package com.bitroot.trainee.restapi.firebase.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
internal interface FirebaseEntityJpaRepository : JpaRepository<FirebaseUserEntity, Int> {

    @Query(
        """
        SELECT * FROM firebase WHERE user_id = :userId
    """,
        nativeQuery = true,
    )
    fun findFirebaseTokenByUserId(userId: Long): Optional<FirebaseUserEntity>

    @Query(
        """
      SELECT f.*
      FROM firebase as f
      JOIN participants ON f.user_id = participants.user_id
      WHERE participants.chat_id = :chatId
    """,
        nativeQuery = true,
    )
    fun findFirebaseTokenByChatId(chatId: Int): Set<FirebaseUserEntity>

    @Query(
        """
      SELECT *
      FROM firebase
      WHERE firebase_token = :token
    """,
        nativeQuery = true,
    )
    fun findFirebaseToken(token: String): Optional<FirebaseUserEntity>
}
