package com.bitroot.trainee.restapi.domain.checkin.adapter.outgoing.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
internal interface CheckInEntityJpaRepository : JpaRepository<CheckInEntity, Long> {

    @Query(
        """
    SELECT * FROM check_in
    """,
        nativeQuery = true,
    )
    fun getAllCheckIn(): Set<CheckInEntity>
}
