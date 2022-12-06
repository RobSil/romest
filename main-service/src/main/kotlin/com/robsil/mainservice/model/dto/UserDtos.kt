package com.robsil.mainservice.model

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserRegisterDto(

    @NotBlank
    @Length(min = 4, max = 32)
    val username: String,

    @NotBlank
    @Email
    val email: String,

    @NotBlank
    @Length(min = 6, max = 32)
    val password: String
)

data class UserSimpleDto(
    val username: String,
    val email: String,
)
data class UserInformationDto(
    val id: Long?,
    val username: String,
    val email: String,
    val roles: MutableSet<String>
)
