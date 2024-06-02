package com.bitroot.trainee.restapi.errorhandling

import mu.KotlinLogging
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@RestHandler
class RestExceptionHandler {
    private val logger = KotlinLogging.logger { }

    @ExceptionHandler
    fun handleNotAcceptableArgument(ex: MessageException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.NOT_ACCEPTABLE.value(),
            ex.message.toString(),
            ex.localizedMessage,
        )
        logger.error(ex.localizedMessage)
        return ResponseEntity(errorResponse, HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler
    fun handleDomainNotFoundException(ex: DomainNotFoundException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.message.toString(),
            ex.localizedMessage,
        )
        logger.error(ex.localizedMessage)
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleFileSizeLimitExceededException(ex: FileSizeLimitExceededException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.PAYLOAD_TOO_LARGE.value(),
            ex.message.toString(),
            ex.localizedMessage,
        )
        logger.error(ex.localizedMessage)
        return ResponseEntity(errorResponse, HttpStatus.PAYLOAD_TOO_LARGE)
    }

    @ExceptionHandler(EmailSendExcaption::class)
    fun handleEmailSendExcaption(ex: EmailSendExcaption): ResponseEntity<Any> {
        val errorMessage = ex.message.toString()

        if (errorMessage.contains("Username and Password not accepted")) {
            val errorResponse = ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Username and Password not accepted",
                ex.localizedMessage,
            )
            logger.error(ex.localizedMessage)
            return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
        } else {
            val errorResponse = ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                errorMessage,
                ex.localizedMessage,
            )
            logger.error(ex.localizedMessage)
            return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(
        ex: DataIntegrityViolationException,
    ): ResponseEntity<Any> {
        val constraintMessages = mapOf(
            "users_user_name_key" to "User with this user name already exists",
        )
        val message = ex.localizedMessage
        val errorMessage = constraintMessages.entries.find { message.contains(it.key) }?.value
        val errorResponse = ErrorResponse(
            HttpStatus.CONFLICT.value(),
            errorMessage ?: "Data integrity violation",
            ex.localizedMessage,
        )
        logger.error(ex.localizedMessage)
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(AuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAccessDeniedException(ex: AuthenticationException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Unauthorized",
            ex.localizedMessage,
        )
        logger.error(ex.localizedMessage)
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(UserDisabledException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUserDisabledException(ex: UserDisabledException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Unauthorized",
            ex.localizedMessage,
        )
        logger.error(ex.localizedMessage)
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(TokenExpiredException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleTokenExpiredException(ex: TokenExpiredException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Unauthorized",
            ex.localizedMessage,
        )
        logger.error(ex.localizedMessage)
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(TokenValidationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleTokenValidationException(ex: TokenValidationException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Unauthorized",
            ex.localizedMessage,
        )
        logger.error(ex.localizedMessage)
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(LoginFailedException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleTokenValidationException(ex: LoginFailedException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Not found",
            ex.localizedMessage,
        )
        logger.error(ex.localizedMessage)
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleEmptyResultDataAccessException(ex: EmptyResultDataAccessException): ResponseEntity<Any> {
        val errorResponse = ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal server error",
            ex.localizedMessage,
        )
        logger.error(ex.localizedMessage)
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
