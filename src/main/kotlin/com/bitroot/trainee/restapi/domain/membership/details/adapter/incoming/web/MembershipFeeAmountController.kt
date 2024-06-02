package com.bitroot.trainee.restapi.domain.membership.details.adapter.incoming.web

import com.bitroot.trainee.restapi.appsettings.toMessageBody
import com.bitroot.trainee.restapi.domain.membership.details.MembershipFeeAmountService
import com.bitroot.trainee.restapi.domain.membership.details.adapter.outgoing.web.MembershipFeeAmountDto
import com.bitroot.trainee.restapi.domain.membership.details.common.interfaces.toDto
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
@RequestMapping("/api/amount")
class MembershipFeeAmountController(
    val membershipFeeAmountService: MembershipFeeAmountService,
) {

    @GetMapping("/{schoolId}")
    fun getAllAmountForSchoolDetailsId(@PathVariable schoolId: Long): ResponseEntity<MembershipFeeAmountDto> =
        ResponseEntity.status(HttpStatus.OK).body(
            membershipFeeAmountService.getAllAmountForSchoolDetailsId(schoolId).toDto(),
        )

    @PostMapping
    fun saveMembershipAmount(@RequestBody membershipFee: MembershipFeeAmountRequest): ResponseEntity<MessageBody> =
        ResponseEntity.status(HttpStatus.OK).body(membershipFeeAmountService.save(membershipFee).toMessageBody())
}
