package com.robsil.data.repository

import com.robsil.data.domain.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {

    fun findByTitle(title: String): Role?
}