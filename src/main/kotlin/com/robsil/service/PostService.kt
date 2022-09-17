package com.robsil.service

import com.robsil.data.domain.Board
import com.robsil.data.domain.Post
import com.robsil.model.dto.PostCreateDto

interface PostService {

    fun getAll(): List<Post>

    fun getPostsByBoard(boardId: Long): List<Post>

    fun saveEntity(post: Post): Post

    fun create(dto: PostCreateDto): Post

}