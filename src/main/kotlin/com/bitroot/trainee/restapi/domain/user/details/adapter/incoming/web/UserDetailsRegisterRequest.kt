package com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web

import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevel
import com.bitroot.trainee.restapi.domain.level.common.interfaces.TrainingLevelClassificationName
import com.bitroot.trainee.restapi.domain.role.common.interfaces.Role
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleId
import com.bitroot.trainee.restapi.domain.role.common.interfaces.RoleName
import com.bitroot.trainee.restapi.domain.school.common.interfaces.School
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolId
import com.bitroot.trainee.restapi.domain.school.common.interfaces.SchoolName
import com.bitroot.trainee.restapi.domain.school.details.adapter.incoming.web.SchoolDetailsRequest
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolCountry
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetails
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolDetailsId
import com.bitroot.trainee.restapi.domain.school.details.common.interfaces.SchoolLocation
import com.bitroot.trainee.restapi.domain.user.common.interfaces.Password
import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserEnabled
import com.bitroot.trainee.restapi.domain.user.common.interfaces.UserName
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetailsEmail
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetailsLastName
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetailsName
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetailsPhoneNumber
import java.time.LocalDateTime

data class UserDetailsRegisterRequest(
    val id: Long? = null,
    val name: String,
    val lastName: String,
    val email: String?,
    val phoneNumber: String?,
    val schoolDetailsRequest: SchoolDetailsRequest,
    val trainingLevel: String?,
)

fun UserDetailsRegisterRequest.requestToDomain(): UserDetails =
    UserDetails(
        user = User(
            userName = UserName(""),
            password = Password(""),
            createdAt = LocalDateTime.now(),
            lastTimeOnline = LocalDateTime.now(),
            role = Role(
                RoleId(null),
                RoleName(""),
            ),
            isEnabled = UserEnabled(true),
        ),
        name = UserDetailsName(this.name),
        lastName = UserDetailsLastName(this.lastName),
        email = UserDetailsEmail(this.email),
        phoneNumber = UserDetailsPhoneNumber(this.phoneNumber),
        schoolDetails = SchoolDetails(
            id = SchoolDetailsId(this.schoolDetailsRequest.id),
            school = School(
                schoolId = SchoolId(this.schoolDetailsRequest.school.schoolId),
                schoolName = SchoolName(this.schoolDetailsRequest.school.schoolName),
            ),
            schoolLocation = SchoolLocation(this.schoolDetailsRequest.schoolLocation),
            schoolCountry = SchoolCountry(this.schoolDetailsRequest.schoolCountry),
        ),
        trainingLevel = TrainingLevel(trainingLevelClassificationName = TrainingLevelClassificationName(this.trainingLevel)),
    )
