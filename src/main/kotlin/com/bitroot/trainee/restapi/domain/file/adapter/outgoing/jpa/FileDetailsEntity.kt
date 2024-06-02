package com.bitroot.trainee.restapi.domain.file.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetails
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsId
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsName
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileDetailsType
import com.bitroot.trainee.restapi.domain.file.common.interfaces.FileViewStatus
import com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.jpa.SectionEntity
import com.bitroot.trainee.restapi.domain.file.details.section.adapter.outgoing.jpa.toDomain
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
@Table(name = "file_details")
data class FileDetailsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name", nullable = false, unique = true)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", referencedColumnName = "id", nullable = false)
    val section: SectionEntity,

    @Column(name = "file_view_status", nullable = false)
    val fileViewStatus: String,

    @Column(name = "file_type", nullable = false)
    val fileType: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,
)

fun FileDetailsEntity.toDomain(): FileDetails =
    FileDetails(
        id = FileDetailsId(this.id),
        name = FileDetailsName(this.name),
        section = this.section.toDomain(),
        fileViewStatus = FileViewStatus.valueOf(this.fileViewStatus),
        createdAt = this.createdAt,
        fileType = FileDetailsType(this.fileType),
    )
