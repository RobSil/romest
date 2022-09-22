package com.robsil.service.facade

import com.robsil.data.domain.Post
import com.robsil.model.dto.PostCreateDto
import org.springframework.web.multipart.MultipartFile

interface PostServiceFacade {

    fun getAllByBoardId(boardId: Long): List<Post>

    fun savePost(dto: PostCreateDto, multipartFile: MultipartFile): Post
}