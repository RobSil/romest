package com.robsil.service.impl

import com.robsil.data.domain.Photo
import com.robsil.data.repository.PhotoRepository
import com.robsil.service.PhotoService
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