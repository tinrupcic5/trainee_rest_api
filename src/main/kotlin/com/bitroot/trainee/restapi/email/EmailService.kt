package com.bitroot.trainee.restapi.email

import com.bitroot.trainee.restapi.domain.membership.common.interfaces.MembershipFee
import com.bitroot.trainee.restapi.domain.settings.common.interfaces.Language
import com.bitroot.trainee.restapi.domain.user.details.common.interfaces.UserDetails
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.EmailSentResult
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.PasswordResetKeyEmailResult
import com.bitroot.trainee.restapi.security.passwordkey.port.outgoing.TemporaryPasswordEmailResult

interface EmailService {

    fun sendTemporaryPasswordToRegisteredUser(
        to: String,
        name: String,
        password: String,
        language: Language,
        schoolName: String,
    ): TemporaryPasswordEmailResult
    fun sendKeyPasswordEmail(to: String, userDetails: UserDetails, key: String, expires: String): PasswordResetKeyEmailResult
    fun sendWarningPaymentEmail(membershipFee: List<MembershipFee>): EmailSentResult
    fun sendSuccessfullRegistrationEmailMessage(to: String, userDetails: UserDetails, schoolName: String, languageSettings: Language? = null): EmailSentResult
}
