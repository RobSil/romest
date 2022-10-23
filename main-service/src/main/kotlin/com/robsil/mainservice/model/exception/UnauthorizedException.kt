package com.robsil.mainservice.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class UnauthorizedException : HttpException {
    constructor() : super(HttpStatus.UNAUTHORIZED.value())
    constructor(message: String?) : super(message, HttpStatus.UNAUTHORIZED.value())
    constructor(message: String?, cause: Throwable?) : super(message, cause, HttpStatus.UNAUTHORIZED.value())
    constructor(cause: Throwable?) : super(cause, HttpStatus.UNAUTHORIZED.value())
}