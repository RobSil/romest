package com.robsil.mainservice.model.exception

import org.springframework.http.HttpStatus

class NotFoundException : HttpException {

    constructor() : super(HttpStatus.NOT_FOUND.value())
    constructor(message: String?) : super(message, HttpStatus.NOT_FOUND.value())
    constructor(message: String?, cause: Throwable?) : super(message, cause, HttpStatus.NOT_FOUND.value())
}
