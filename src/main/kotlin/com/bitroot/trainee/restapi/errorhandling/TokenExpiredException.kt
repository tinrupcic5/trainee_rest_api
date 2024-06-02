package com.bitroot.trainee.restapi.errorhandling

import java.lang.Exception

class TokenExpiredException(val value: String) : Exception(value)
