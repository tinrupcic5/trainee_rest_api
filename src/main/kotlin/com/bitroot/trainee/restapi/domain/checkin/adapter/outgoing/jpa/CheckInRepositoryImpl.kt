package com.bitroot.trainee.restapi.domain.checkin.adapter.outgoing.jpa

import com.bitroot.trainee.restapi.domain.checkin.common.interfaces.CheckIn
import com.bitroot.trainee.restapi.domain.checkin.common.interfaces.toEntity
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
@Transactional(Transactional.TxType.REQUIRED)
internal class CheckInRepositoryImpl(
    val checkInEntityJpaRepository: CheckInEntityJpaRepository,
) : CheckInRepository {
    override fun save(checkIn: CheckIn): String {
        checkInEntityJpaRepository.save(checkIn.toEntity())
        return "Check in saved."
    }
}
