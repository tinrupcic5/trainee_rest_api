package com.bitroot.trainee.restapi.domain.user.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.user.common.interfaces.User
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import java.util.Optional

interface UserRepository {
    fun register(
        user: User,
        userDetails: UserDetails,
        language: String,
    ): String

    fun saveLastTimeOnline(user: User): String

    fun saveGuest(
        user: User,
        language: String,
    ): UserDetails

    fun updateUserPassword(
        userId: Long,
        newPassword: String,
    ): Boolean

    fun login(
        username: String,
        password: String,
    ): Optional<User>

    fun getUserByUsername(username: String): Optional<UserDetails>

    fun getUserById(userId: Long): User

    fun deleteUser(userId: Long): String

    fun setUserEnableStatus(copy: User): String

    fun getAllUsers(): List<User>?
}
