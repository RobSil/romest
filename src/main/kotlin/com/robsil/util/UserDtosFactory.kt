package com.robsil.util

import com.robsil.data.domain.User
import com.robsil.model.UserInformationDto

fun User.toInformationDto(): UserInformationDto {
    return if (this.id != null) UserInformationDto(this.id, this.email, this.roles.map { it.title }.toSet())
            else UserInformationDto(-1L, this.email, this.roles.map { it.title }.toSet())
}