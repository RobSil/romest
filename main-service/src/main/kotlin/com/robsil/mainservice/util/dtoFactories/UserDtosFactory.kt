package com.robsil.mainservice.util.dtoFactories

import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.UserInformationDto

class UserDtosFactory {

}

//fun User.toInformationDto(): UserInformationDto {
//    return UserInformationDto(this.id, this.username, this.email, this.roles.map { it.title }.toMutableSet())
//    return if (this.id != null) UserInformationDto(this.id, this.email, this.roles.map { it.title }.toSet())
//    else UserInformationDto(-1L, this.email, this.roles.map { it.title }.toSet())
//}