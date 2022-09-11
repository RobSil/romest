package com.robsil.service

import com.robsil.data.domain.Photo

interface PhotoService {
    fun saveEntity(photo: Photo): Photo
}