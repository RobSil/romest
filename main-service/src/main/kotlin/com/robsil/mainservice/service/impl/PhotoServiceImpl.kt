package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Photo
import com.robsil.mainservice.data.repository.PhotoRepository
import com.robsil.mainservice.model.exception.ExceptionMessages
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.model.exception.constant.PostExceptionMessages
import com.robsil.mainservice.service.PhotoService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PhotoServiceImpl(
    private val photoRepository: PhotoRepository
) : PhotoService {

    val log = logger()

    override fun getById(id: Long): Photo {
        return photoRepository.findById(id).orElse(null)
            ?: run {
                log.info("getById: photo not found. ID: $id")
                throw NotFoundException(ExceptionMessages.PHOTO_NOT_FOUND)
            }
    }

    override fun saveEntity(photo: Photo): Photo {
        return photoRepository.save(photo)
    }

    override fun deleteById(id: Long) {
        photoRepository.deleteById(id)
    }
}