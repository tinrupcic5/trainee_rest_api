package com.bitroot.trainee.restapi.errorhandling

class LoginFailedException(val value: String) : Exception(value)
