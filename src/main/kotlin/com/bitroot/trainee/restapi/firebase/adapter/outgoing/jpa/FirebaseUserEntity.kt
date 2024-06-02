package com.bitroot.trainee.restapi.firebase.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.UserEntity
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.domain.user.common.interfaces.domainToEntity
import com.bitroot.trainee.restapi.firebase.adapter.outgoing.web.FirebaseTokenDto
import com.bitroot.trainee.restapi.firebase.common.interfaces.FirebaseUser
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "firebase")
data class FirebaseUserEntity(
    @Id
    @Column(name = "firebase_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val firebaseId: Long? = null,

    @Column(name = "firebase_token")
    val firebaseToken: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity,
)

fun Set<FirebaseUserEntity>.setEntityToDomain(): Set<FirebaseUser> {
    return map {
        FirebaseUser(
            firebaseId = it.firebaseId,
            firebaseToken = it.firebaseToken,
            user = it.user.toDomain(),
        )
    }.toSet()
}

fun Set<FirebaseUser>.setDomainToDto(): Set<FirebaseTokenDto> {
    return map {
        FirebaseTokenDto(
            firebaseToken = it.firebaseToken,
        )
    }.toSet()
}

fun FirebaseUserEntity.toDto() =
    FirebaseTokenDto(
        firebaseToken = this.firebaseToken,
    )

fun FirebaseUserEntity.entityToDomain() =
    FirebaseUser(
        firebaseId = this.firebaseId,
        firebaseToken = this.firebaseToken,
        user = this.user.toDomain(),
    )

fun FirebaseUser.toEntity() =
    FirebaseUserEntity(
        firebaseId = this.firebaseId,
        firebaseToken = this.firebaseToken,
        user = this.user.domainToEntity(),
    )

fun FirebaseUser.domainToDto() =
    FirebaseTokenDto(
        firebaseToken = this.firebaseToken,
    )
