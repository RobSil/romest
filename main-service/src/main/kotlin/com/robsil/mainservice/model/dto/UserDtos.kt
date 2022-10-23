package com.robsil.mainservice.model

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserRegisterDto(
    @NotBlank
    @Email
    val email: String,

    @NotBlank
    @Length(min = 6, max = 32)
    val password: String
)

data class UserInformationDto(
    val id: Long?,
    val email: String,
    val roles: Set<String>
)