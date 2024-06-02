package com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails

interface UserDetailsRepository {

    fun getUserDetailsByUserId(userId: Long): UserDetails

    fun saveUserDetails(userDetails: UserDetails): String

    fun getUserDetailsByTrainingLevelClassification(t: TrainingCalendarRequest): List<UserDetails>
}
