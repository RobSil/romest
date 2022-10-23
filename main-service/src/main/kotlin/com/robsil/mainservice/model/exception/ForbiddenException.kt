package com.robsil.mainservice.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class ForbiddenException : HttpException {
    constructor() : super(HttpStatus.FORBIDDEN.value())
    constructor(message: String?) : super(message, HttpStatus.FORBIDDEN.value())
    constructor(message: String?, cause: Throwable?) : super(message, cause, HttpStatus.FORBIDDEN.value())
    constructor(cause: Throwable?) : super(cause, HttpStatus.FORBIDDEN.value())
}