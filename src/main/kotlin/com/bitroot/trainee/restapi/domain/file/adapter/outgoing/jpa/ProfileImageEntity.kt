package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImage
import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImageId
import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImageName
import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImageNameClassPath
import com.bitroot.trainee.restapi.domain.file.common.interfaces.ProfileImageNameSuffix
import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.jpa.SchoolEntity
import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.jpa.entityToDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "profile_image")
data class ProfileImageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "name")
    val name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    val schoolEntity: SchoolEntity,
    @Column(name = "suffix")
    val suffix: String,
    @Column(name = "class_path")
    val classPath: String,
    @Column(name = "created_at")
    val createdAt: LocalDateTime,
)

fun ProfileImageEntity.toDomain() =
    ProfileImage(
        id = ProfileImageId(this.id),
        school = this.schoolEntity.entityToDomain(),
        name = ProfileImageName(this.name),
        suffix = ProfileImageNameSuffix(this.suffix),
        classPath = ProfileImageNameClassPath(this.classPath),
        createdAt = this.createdAt,
    )
