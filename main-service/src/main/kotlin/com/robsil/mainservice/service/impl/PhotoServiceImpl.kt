package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Photo
import com.robsil.mainservice.data.repository.PhotoRepository
import com.robsil.mainservice.service.PhotoService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PhotoServiceImpl(
    private val photoRepository: PhotoRepository
) : PhotoService {
    override fun saveEntity(photo: Photo): Photo {
        return photoRepository.save(photo)
    }
}