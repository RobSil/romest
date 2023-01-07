package com.robsil.mainservice.data.repository

import com.robsil.mainservice.data.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?
}
