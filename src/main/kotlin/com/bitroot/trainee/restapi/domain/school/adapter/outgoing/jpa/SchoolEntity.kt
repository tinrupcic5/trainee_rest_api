package com.bitroot.trainee.restapi.domain.school.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.school.common.interfaces.School
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolId
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolName
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "school")
data class SchoolEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val schoolName: String,
)

fun SchoolEntity.entityToDomain(): School =
    School(
        schoolId = SchoolId(this.id),
        schoolName = SchoolName(this.schoolName),
    )
