package com.robsil.mainservice.service.facade

import com.robsil.mainservice.data.domain.Photo
import com.robsil.mainservice.model.dto.SimplePhotoDto
import org.springframework.web.multipart.MultipartFile

interface PhotoServiceFacade {

    fun toSimplePhotoDto(photo: Photo): SimplePhotoDto

    fun save(minimizedBoardName: String, multipartFile: MultipartFile): Photo

    fun deleteById(id: Long)
}
