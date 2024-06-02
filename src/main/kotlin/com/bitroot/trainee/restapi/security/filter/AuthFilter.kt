package com.bitroot.trainee.restapi.security.filter

import com.bitroot.trainee.restapi.errorhandling.TokenExpiredException
import com.bitroot.trainee.restapi.errorhandling.TokenValidationException
import com.bitroot.trainee.restapi.security.jwt.JwtService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.servlet.HandlerInterceptor

@Service
class AuthFilter(val jwtService: JwtService) : HandlerInterceptor {

    var log = LoggerFactory.getLogger(this.javaClass)!!

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        return try {
            val authorizationHeader = request.getHeader("Authorization")
                ?: run {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing")
                    throw JwtException("Authorization header should not be null")
                }

            val token = authorizationHeader.substringOrNull(7)
                ?: run {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is missing or invalid")
                    throw JwtException("Token should not be null")
                }

            if (jwtService.isTokenInvalid(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token")
                throw TokenExpiredException("Invalid token")
            }
            jwtService.getJwtClaims(authorizationHeader)
            true
        } catch (e: ExpiredJwtException) {
            throw TokenExpiredException("JWT token expired: ${e.message}")
        } catch (e: JwtException) {
            throw TokenValidationException("JWT validation failed: ${e.message}")
        }
    }

    // Extension function to safely get a substring or return null
    fun String.substringOrNull(startIndex: Int): String? {
        return if (startIndex in 0 until length) {
            substring(startIndex)
        } else {
            null
        }
    }
}
