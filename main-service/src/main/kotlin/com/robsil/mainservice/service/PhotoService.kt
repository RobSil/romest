package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Photo

interface PhotoService {

    fun getById(id: Long): Photo

    fun saveEntity(photo: Photo): Photo

    fun deleteById(id: Long): Unit
}