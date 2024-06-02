package com.bitroot.trainee.restapi.firebase.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.firebase.common.interfaces.FirebaseUser
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class FirebaseRepositoryImpl(
    private val firebaseEntityJpaRepository: FirebaseEntityJpaRepository,
) : FirebaseRepository {
    override fun findFirebaseTokenByUserId(userId: Long): Optional<FirebaseUser> =
        firebaseEntityJpaRepository.findFirebaseTokenByUserId(userId).map { it.entityToDomain() }

    override fun findFirebaseToken(token: String): Optional<FirebaseUser> =
        firebaseEntityJpaRepository.findFirebaseToken(token).map { it.entityToDomain() }

    @Modifying
    override fun save(firebaseUser: FirebaseUser): String {
        firebaseEntityJpaRepository.save(firebaseUser.toEntity())
        return "FirebaseUser saved."
    }
}
