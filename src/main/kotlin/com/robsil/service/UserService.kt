package com.robsil.service

import com.robsil.data.domain.User
import com.robsil.model.UserRegisterDto
import java.security.Principal

interface UserService {

    fun getAll(): List<User>

    fun getById(id: Long): User
    fun getByEmail(email: String): User

    fun getByPrincipal(principal: Principal?): User

    fun register(dto: UserRegisterDto): User

    fun getUserInformation(user: User): User

}