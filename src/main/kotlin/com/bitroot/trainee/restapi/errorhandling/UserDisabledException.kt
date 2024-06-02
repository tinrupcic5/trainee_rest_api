package com.bitroot.trainee.restapi.errorhandling

import java.lang.Exception

class UserDisabledException(val value: String) : Exception(value)
