package com.robsil.service.facade

import com.robsil.data.domain.Photo
import com.robsil.data.domain.Post
import org.springframework.web.multipart.MultipartFile

interface PhotoServiceFacade {

    fun save(post: Post, minimizedBoardName: String, multipartFile: MultipartFile): Photo
}