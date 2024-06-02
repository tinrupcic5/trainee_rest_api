package com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.Section
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.SectionId
import com.bitroot.trainee.restapi.domain.file.details.section.common.interfaces.SectionName
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.UserDetailsEntity
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa.entityToDomain
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
@Table(name = "section")
data class SectionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name", nullable = false, unique = true)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_details_id", referencedColumnName = "id")
    val userDetails: UserDetailsEntity,

)

fun SectionEntity.toDomain(): Section =
    Section(
        id = SectionId(this.id),
        name = SectionName(this.name),
        userDetails = this.userDetails.entityToDomain(),
    )
