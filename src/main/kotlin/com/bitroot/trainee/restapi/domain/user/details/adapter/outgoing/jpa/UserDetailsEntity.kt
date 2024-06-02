package com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa.TrainingLevelEntity
import com.bitroot.trainee.restapi.domain.level.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.SchoolDetailsEntity
import com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.UserEntity
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa.toDomain
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetailsEmail
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetailsId
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetailsLastName
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetailsName
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetailsPhoneNumber
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_details")
data class UserDetailsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: UserEntity,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val lastName: String,

    val email: String?,

    @Column(unique = true)
    val phoneNumber: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_details_id", referencedColumnName = "id")
    val schoolDetailsEntity: SchoolDetailsEntity,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_level_classification_system_id", referencedColumnName = "id")
    val trainingLevelEntity: TrainingLevelEntity? = null,
)

fun UserDetailsEntity.entityToDomain(): UserDetails =
    UserDetails(
        id = UserDetailsId(this.id!!),
        user = this.user.toDomain(),
        name = UserDetailsName(this.name),
        lastName = UserDetailsLastName(this.lastName),
        email = UserDetailsEmail(this.email),
        phoneNumber = UserDetailsPhoneNumber(this.phoneNumber),
        schoolDetails = this.schoolDetailsEntity.toDomain(),
        trainingLevel = this.trainingLevelEntity?.toDomain(),
    )
