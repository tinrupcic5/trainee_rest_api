package com.bitroot.trainee.restapi.domain.membership.adapter.incoming.scheduler

import com.bitroot.trainee.restapi.domain.membership.MembershipFeeService
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledMembershipCheck(
    val membershipFeeService: MembershipFeeService,
) {

    private val logger = KotlinLogging.logger { }

    @Scheduled(cron = "\${trainee.jobs.fee-schedule.cron}", zone = "\${trainee.jobs.fee-schedule.time-zone}")
    fun membershipCheck() {
        LoggingContext.put(LoggingContext.UseCase.MEMBERSHIP_CHECK)
        logger.info (LoggingContext.UseCase.MEMBERSHIP_CHECK.name)
        membershipFeeService.membershipCheck()

    }
}
