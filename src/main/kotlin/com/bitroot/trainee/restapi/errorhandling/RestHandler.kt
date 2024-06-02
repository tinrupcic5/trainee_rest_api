package com.bitroot.trainee.restapi.errorhandling

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ResponseBody

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@ControllerAdvice
@ResponseBody
annotation class RestHandler
