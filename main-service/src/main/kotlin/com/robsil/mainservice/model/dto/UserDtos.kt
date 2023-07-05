package com.robsil.mainservice.model

import com.robsil.mainservice.model.dto.SimplePhotoDto
import org.hibernate.validator.constraints.Length
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserRegisterDto(

    @field:NotBlank
    @field:Length(min = 4, max = 32)
    val username: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    @field:Length(min = 6, max = 32)
    val password: String
)

data class UserSimpleDto(
    val username: String,
    val email: String,
)

data class ComplexUserDto(
    val username: String,
    val avatar: SimplePhotoDto
)

data class UserInformationDto(
    val id: Long?,
    val username: String,
    val email: String,
    val roles: MutableSet<String>
)
