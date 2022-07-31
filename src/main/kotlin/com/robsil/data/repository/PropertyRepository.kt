package com.robsil.data.repository

import com.robsil.data.domain.Property
import org.springframework.data.jpa.repository.JpaRepository

interface PropertyRepository: JpaRepository<Property, Long> {
    fun findByName(name: String): Property?
}