package com.robsil.mainservice.model.exception

import org.springframework.http.HttpStatus

class IllegalRequestPayloadException : HttpException {

    constructor(message: String?) : super(message, HttpStatus.BAD_REQUEST.value())
    constructor(message: String?, cause: Throwable?) : super(message, cause, HttpStatus.BAD_REQUEST.value())
}
