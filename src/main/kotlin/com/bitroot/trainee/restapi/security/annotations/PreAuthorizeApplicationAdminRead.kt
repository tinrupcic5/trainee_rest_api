package com.audi.awsi.service.usedcarplatformmonolithicbackend.domain.common.security.annotations

import org.springframework.security.access.prepost.PreAuthorize

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MustBeDocumented
@PreAuthorize("hasAnyAuthority('OP_AUCTION_ADMIN_READ', 'OP_MARKET_ADMIN_READ')")
annotation class PreAuthorizeApplicationAdminRead
