package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.model.dto.PostCreateDto
import com.robsil.mainservice.model.dto.PostSaveDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostService {

    fun getById(id: Long): Post

    fun getAllByBoardId(boardId: Long): List<Post>

    fun getAllByBoardId(boardId: Long, pageable: Pageable): Page<Post>
    fun getAllByBoard(board: Board): List<Post>

    fun getAllByTagsRelevant(tags: List<Long>, pageable: Pageable): Page<Post>
    fun getAllByTagsRelevant(tags: List<Long>, userId: Long, pageable: Pageable): Page<Post>

    fun getAllByUsername(username: String, pageable: Pageable): Page<Post>
    fun getAllLikedByUsername(username: String, pageable: Pageable): Page<Post>

    fun saveEntity(post: Post): Post

    fun create(dto: PostCreateDto): Post

    fun save(dto: PostSaveDto): Post

    fun deleteById(postId: Long): Unit

}
