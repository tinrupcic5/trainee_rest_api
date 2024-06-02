package com.bitroot.trainee.restapi.domain.user.details

import com.bitroot.trainee.restapi.domain.user.details.adapter.incoming.web.UserDetailsRequest

interface UserDetailsService {
    fun saveUserDetails(userDetailsRequest: UserDetailsRequest): String
}
