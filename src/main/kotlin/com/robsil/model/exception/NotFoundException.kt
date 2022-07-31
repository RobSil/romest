package com.robsil.model.exception

class NotFoundException : RuntimeException {
    constructor(): super() {}
    constructor(message: String): super(message) {}
}