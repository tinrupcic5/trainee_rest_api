package com.bitroot.trainee.restapi.security.jwt

import com.bitroot.trainee.restapi.domain.user.UserService
import com.bitroot.trainee.restapi.errorhandling.UserDisabledException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    val userService: UserService,
) {

    @Value("\${jwt-security.expiration-time}")
    val expiration: Int = 600

    @Value("\${jwt-security.secret}")
    private val secret: String = ""
    @Value("\${jwt-security.refresh-expiration-time}")
    val refreshExpiration: Int = 86400 // 24 hours

    lateinit var secretKey: SecretKey

    private val invalidatedTokens: MutableSet<String> = Collections.synchronizedSet(HashSet())

    @PostConstruct
    fun init() {
        secretKey = Keys.hmacShaKeyFor(secret.toByteArray())
    }

    // consider using different jwt per different endpoint, each with his own secret key and exp time
    fun create(userId: Long?): String {
        val userResult = userService.getUserById(userId!!)
        if (!userResult.isEnabled) {
            throw UserDisabledException("User is disabled")
        }
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, expiration)

        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(Date()) // consider using this instead of (or with) exp, and validate manually (iat + 10 min < now )
            .setExpiration(calendar.time)
            .setNotBefore(Date())
            .signWith(secretKey)
            .compact()
    }
    fun invalidateToken(token: String) {
        invalidatedTokens.add(token)
    }

    fun createRefreshToken(userId: Long): String {
        val userResult = userService.getUserById(userId)
        if (!userResult.isEnabled) {
            throw UserDisabledException("User is disabled")
        }
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, refreshExpiration)

        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(Date())
            .setExpiration(calendar.time)
            .setNotBefore(Date())
            .signWith(secretKey)
            .compact()
    }

    fun validateRefreshToken(token: String): Long {
        try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body

            val userId = claims.subject.toLong()

            if (isTokenInvalid(token)) {
                throw MalformedJwtException("Token is invalidated")
            }
            return userId
        } catch (e: Exception) {
            throw MalformedJwtException("Invalid refresh token")
        }
    }

    fun isTokenInvalid(token: String): Boolean {
        return invalidatedTokens.contains(token)
    }
    fun getJwtClaims(authorizationHeader: String): Jws<Claims> {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(getJwtFromHeader(authorizationHeader))
    }

    fun getJwtFromHeader(authorizationHeader: String): String {
        val jwt = authorizationHeader.split("Bearer ")
        return if (jwt.size == 2) {
            jwt[1]
        } else {
            throw MalformedJwtException("Header is not a valid jwt!")
        }
    }
}
