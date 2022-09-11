package com.robsil.data.repository

import com.robsil.data.domain.Photo
import org.springframework.data.jpa.repository.JpaRepository

interface PhotoRepository : JpaRepository<Photo, Long> {
}