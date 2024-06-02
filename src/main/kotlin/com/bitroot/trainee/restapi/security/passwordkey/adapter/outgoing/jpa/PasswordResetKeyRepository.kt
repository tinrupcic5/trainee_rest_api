package com.bitroot.trainee.restapi.security.passwordkey.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.security.passwordkey.common.interfaces.PasswordResetKey

interface PasswordResetKeyRepository {
    fun createPasswordKey(passwordResetKey: PasswordResetKey): PasswordResetKey
    fun getPasswordKeyByUserId(userId: Long): PasswordResetKey?

    fun updatePasswordResetKeyIsUsed(userId: Long, isUsed: Boolean): Boolean

    fun checkIfKeyIsValidForUser(userId: Long, key: String): String
}
