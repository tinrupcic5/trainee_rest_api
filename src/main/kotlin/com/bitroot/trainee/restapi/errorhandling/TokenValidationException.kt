package com.bitroot.trainee.restapi.errorhandling

import java.lang.Exception

class TokenValidationException(val value: String) : Exception(value)
