package com.bitroot.trainee.restapi.domain.user

import com.bitroot.trainee.restapi.domain.training.details.calendar.adapter.outgoing.web.TrainingCalendarRequest
import com.bitroot.trainee.restapi.domain.user.adapter.incoming.web.UserChangePasswordRequest
import com.bitroot.trainee.restapi.domain.user.adapter.incoming.web.UserRegisterRequest
import com.bitroot.trainee.restapi.domain.user.adapter.outgoing.web.UserDto
import com.bitroot.trainee.restapi.domain.user.details.adapter.outgoing.web.UserDetailsDto
import java.util.*

interface UserService {

    /**
     * if user is registered by the admin and it does not have a password, it will be created automatically
     */
    fun register(user: UserRegisterRequest): String
    fun saveGuest(language: String): UserDetailsDto
    fun login(username: String, password: String): Optional<UserDetailsDto>
    fun getUserById(id: Long): UserDto
    fun getAllUsers(): List<UserDto>?
    fun changePassword(userChangePasswordRequest: UserChangePasswordRequest): String
    fun deleteUser(userId: Long): String
    fun setUserEnableStatus(userId: Long, enabled: Boolean): String
    fun getUserDetailsByTrainingLevelClassification(t: TrainingCalendarRequest): List<UserDetailsDto>
}
