package com.robsil.mainservice.model.exception

data class Violation(
    val fieldName: String,
    val message: String?
)
