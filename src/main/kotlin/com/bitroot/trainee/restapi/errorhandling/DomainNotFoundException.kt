package com.bitroot.trainee.restapi.errorhandling

import java.lang.Exception

class DomainNotFoundException(val value: String) : Exception(value)
