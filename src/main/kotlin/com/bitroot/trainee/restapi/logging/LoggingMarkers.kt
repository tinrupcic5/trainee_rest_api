package com.audi.awsi.service.usedcarplatformmonolithicbackend.domain.common.logging

import org.slf4j.Marker
import org.slf4j.MarkerFactory

object LoggingMarkers {
    @JvmStatic
    val SECURITY: Marker = MarkerFactory.getMarker("SECURITY")
}
