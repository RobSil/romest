package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.model.dto.PostCreateDto

interface PostService {

    fun getAll(): List<Post>

    fun getPostsByBoard(boardId: Long): List<Post>

    fun getPostsByBoardId(boardId: Long): List<Post>

    fun saveEntity(post: Post): Post

    fun create(dto: PostCreateDto): Post

    fun deleteById(postId: Long): Unit

}