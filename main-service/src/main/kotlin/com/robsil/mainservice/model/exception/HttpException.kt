package com.robsil.mainservice.model.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties("cause", "stackTrace", "suppressed", "localizedMessage", "trace")
abstract class HttpException: RuntimeException {

    val timestamp: LocalDateTime = LocalDateTime.now()

    val status: Int

    constructor(status: Int) : super() {
        this.status = status
    }

    constructor(message: String?, status: Int) : super(message) {
        this.status = status
    }

    constructor(message: String?, cause: Throwable?, status: Int) : super(message, cause) {
        this.status = status
    }

    constructor(cause: Throwable?, status: Int) : super(cause) {
        this.status = status
    }
}