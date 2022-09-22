package com.robsil.model.exception

// exception for server errors, but not critical! Critical errors obviously should be omitted or got exposed by Spring/Kotlin?
class InternalServerErrorException : RuntimeException {

    constructor(message: String?) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)

}