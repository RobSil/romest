package com.robsil.mainservice.data.repository

import com.robsil.mainservice.data.domain.Property
import org.springframework.data.jpa.repository.JpaRepository

interface PropertyRepository: JpaRepository<Property, Long> {
    fun findByName(name: String): Property?
}