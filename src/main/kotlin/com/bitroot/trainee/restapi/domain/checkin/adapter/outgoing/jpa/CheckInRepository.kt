package com.bitroot.trainee.restapi.domain.checkin.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.checkin.common.interfaces.CheckIn

interface CheckInRepository {

    fun save(checkIn: CheckIn): String
}
