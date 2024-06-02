package com.bitroot.trainee.restapi.errorhandling

import java.lang.Exception

class EmailSendExcaption(val value: String) : Exception(value)
