package com.robsil.model

data class UserRegisterDto(
    val email: String,
    val password: String
)

data class UserInformationDto(
    val id: Long,
    val email: String,
    val roles: Set<String>
)