package com.robsil.mainservice.data.repository

import com.robsil.mainservice.data.domain.Photo
import org.springframework.data.jpa.repository.JpaRepository

interface PhotoRepository : JpaRepository<Photo, Long> {
}