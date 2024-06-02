package com.bitroot.trainee.restapi.domain.membership.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.membership.MembershipFeeService
import com.bitroot.trainee.restapi.domain.membership.adapter.outgoing.web.MembershipFeeDto
import com.bitroot.trainee.restapi.domain.membership.common.interfaces.toDto
import com.bitroot.trainee.restapi.domain.user.MessageBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/membership")
class MembershipFeeController(
    val membershipFeeService: MembershipFeeService,
) {

    @GetMapping
    fun getAllMembership(@PathVariable schoolId: Long): ResponseEntity<List<MembershipFeeDto>> =
        ResponseEntity.status(HttpStatus.OK).body(
            membershipFeeService.getAllMembershipBySchool(schoolId).map { it.toDto() },
        )

    @GetMapping("/{year}/{month}/{schoolId}")
    fun getAllMembershipByTheDate(@PathVariable year: Int, @PathVariable month: Int, @PathVariable schoolId: Long): ResponseEntity<List<MembershipFeeDto>> =
        ResponseEntity.status(HttpStatus.OK).body(
            membershipFeeService.getAllMembershipForExactMonth(year, month, schoolId).map { it.toDto() },
        )

    // add new payment, upsert payment or cancel payment
    @PostMapping("/pay")
    fun payMembership(@RequestBody membershipFee: MembershipFeeRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK).body(membershipFeeService.payMembership(membershipFee).toMessageBody())
}
