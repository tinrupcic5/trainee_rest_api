package com.bitroot.trainee.restapi.errorhandling

import java.lang.Exception

class MessageException(val value: String) : Exception(value)
