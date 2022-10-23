package com.robsil.mainservice.service.facade

import com.robsil.mainservice.data.domain.Photo
import com.robsil.mainservice.data.domain.Post
import org.springframework.web.multipart.MultipartFile

interface PhotoServiceFacade {

    fun save(minimizedBoardName: String, multipartFile: MultipartFile): Photo

    fun deleteById(id: Long)
}