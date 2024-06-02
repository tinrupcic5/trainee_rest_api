package com.bitroot.trainee.restapi.domain.school.details.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.school.adapter.outgoing.jpa.SchoolEntity
import com.bitroot.trainee.restapi.domain.school.common.interfaces.School
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolId
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolName
import com.bitroot.trainee.restapi.domain.school.common.interfaces.domainToEntity
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolCountry
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetailsId
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolLocation
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
@Table(name = "school_details")
data class SchoolDetailsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    val schoolEntity: SchoolEntity,

    @Column(nullable = false)
    val schoolLocation: String,

    @Column(nullable = false)
    val schoolCountry: String,
)

fun SchoolDetailsEntity.toDomain(): SchoolDetails =
    SchoolDetails(
        id = SchoolDetailsId(this.id),
        school = School(
            schoolId = SchoolId(this.schoolEntity.id),
            schoolName = SchoolName(this.schoolEntity.schoolName),
        ),
        schoolLocation = SchoolLocation(this.schoolLocation),
        schoolCountry = SchoolCountry(this.schoolCountry),
    )

fun SchoolDetails.domainToEntity(): SchoolDetailsEntity =
    SchoolDetailsEntity(
        id = this.id?.value,
        schoolEntity = this.school.domainToEntity(),
        schoolLocation = this.schoolLocation.value,
        schoolCountry = this.schoolCountry.value,
    )
