package com.bitroot.trainee.restapi.domain.membership.adapter.incoming.web

import java.time.LocalDateTime

data class MembershipFeeRequest(
    val id: Long? = null,
    val userId: Long,
    val forMonthAndYear: String,
    val paymentDate: LocalDateTime? = null,
    val isPaid: Boolean,
)
