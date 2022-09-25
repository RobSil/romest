package com.robsil.mainservice.data.repository

import com.robsil.mainservice.data.domain.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long> {

    fun findByTitle(title: String): Role?
}