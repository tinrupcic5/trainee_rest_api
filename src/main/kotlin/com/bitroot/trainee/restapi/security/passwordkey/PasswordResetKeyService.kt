package com.bitroot.trainee.restapi.security.passwordkey

import com.bitroot.trainee.restapi.security.passwordkey.adapter.incoming.web.PasswordResetKeyRequest
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.PasswordResetKeyEmailResult

interface PasswordResetKeyService {

    fun createPasswordKey(passwordResetKeyRequest: PasswordResetKeyRequest): PasswordResetKeyEmailResult

    fun updatePasswordResetKeyIsUsed(id: Long, isUsed: Boolean): Boolean

    fun checkIfKeyIsValidForUser(userId: Long, key: String): String
}
