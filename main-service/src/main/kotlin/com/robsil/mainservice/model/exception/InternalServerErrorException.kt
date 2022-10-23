package com.robsil.mainservice.model.exception

import org.springframework.http.HttpStatus

// exception for server errors, but not critical! Critical errors obviously should be omitted or got exposed by Spring/Kotlin?
class InternalServerErrorException : HttpException {

    constructor(message: String?) : super(message, HttpStatus.INTERNAL_SERVER_ERROR.value())
    constructor(message: String?, cause: Throwable?) : super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR.value())
}