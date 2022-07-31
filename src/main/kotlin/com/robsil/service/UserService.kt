package com.robsil.service

import com.robsil.data.domain.User

interface UserService {

    fun getByEmail(email: String): User

}