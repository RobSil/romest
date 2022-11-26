package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.dto.PostCreateDto
import com.robsil.mainservice.model.dto.PostSaveDto

interface PostService {

    @Deprecated(message = "getAll - is intended only for testing purposes.", level = DeprecationLevel.WARNING)
    fun getAll(): List<Post>

    fun getById(id: Long): Post

    fun getPostsByBoard(boardId: Long): List<Post>

    fun getPostsByBoardId(boardId: Long): List<Post>

    fun getAllByTagsRelevant(tags: List<Long>): List<Post>

    fun getAllByTagsRelevant(tags: List<Long>, userId: Long): List<Post>

    fun saveEntity(post: Post): Post

    fun create(dto: PostCreateDto): Post

    fun save(dto: PostSaveDto): Post

    fun deleteById(postId: Long): Unit

}