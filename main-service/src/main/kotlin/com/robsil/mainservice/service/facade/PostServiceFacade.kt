package com.robsil.mainservice.service.facade

import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.dto.request.PostCreateRequest
import org.springframework.web.multipart.MultipartFile

interface PostServiceFacade {

    fun getAllByBoardId(boardId: Long, user: User): List<Post>

    fun savePost(dto: PostCreateRequest, multipartFile: MultipartFile): Post

    fun deletePost(postId: Long): Unit

    fun getAllByTagsRelevant(): List<Post>
}