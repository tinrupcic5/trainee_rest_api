package com.bitroot.trainee.restapi.domain.membership.adapter.incoming.scheduler

import com.bitroot.trainee.restapi.domain.membership.MembershipFeeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledMembershipCheck(
    val membershipFeeService: MembershipFeeService,
) {
    @Scheduled(cron = "\${trainee.jobs.fee-schedule.cron}", zone = "\${trainee.jobs.fee-schedule.time-zone}")
    fun membershipCheck() {
        LoggingContext.put(LoggingContext.UseCase.MEMBERSHIP_CHECK)
        membershipFeeService.membershipCheck()

    }
}
