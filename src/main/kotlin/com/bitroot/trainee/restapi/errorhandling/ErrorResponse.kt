package com.bitroot.trainee.restapi.errorhandling

data class ErrorResponse(
    val errorCode: Int,
    val errorMessage: String,
    val exceptionMessage: String,
)
