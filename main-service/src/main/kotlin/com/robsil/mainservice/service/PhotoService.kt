package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Photo

interface PhotoService {
    fun saveEntity(photo: Photo): Photo
}