package com.bitroot.trainee.restapi.domain.checkin

import com.bitroot.trainee.restapi.domain.checkin.adapter.incoming.web.CheckInRequest

interface CheckInService {
    fun save(checkInRequest: CheckInRequest): String
}
