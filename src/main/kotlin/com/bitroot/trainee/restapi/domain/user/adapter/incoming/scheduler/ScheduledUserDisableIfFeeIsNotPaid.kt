package com.bitroot.trainee.restapi.domain.user.adapter.incoming.scheduler

import com.bitroot.trainee.restapi.domain.membership.MembershipFeeService
import com.bitroot.trainee.restapi.domain.user.UserService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledUserDisableIfFeeIsNotPaid(
    val membershipFeeService: MembershipFeeService,
    val userService: UserService,
) {
    @Scheduled(cron = "\${trainee.jobs.user-disable-schedule.cron}", zone = "\${trainee.jobs.user-disable-schedule.time-zone}")
    fun membershipCheckForUnpaidFeeOverMonthAndTenDays() {
        LoggingContext.put(LoggingContext.UseCase.MEMBERSHIP_CHECK_FOR_UNPAID_FEE_OVER_MONTH_AND_TEN_DAYS)

        val membershipFeeUsers = membershipFeeService.membershipCheckForUnpaidFeeOverMonthAndTenDays()

        membershipFeeUsers.forEach {
            userService.setUserEnableStatus(it.userDetails.user.id?.value!!, false)
        }
    }
}
