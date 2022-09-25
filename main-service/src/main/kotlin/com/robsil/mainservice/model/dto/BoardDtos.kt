package com.robsil.mainservice.model.dto

import com.robsil.mainservice.data.domain.Post

data class BoardCreateDto(
    val name: String,
    val isPrivate: Boolean
)

data class BoardSaveDto(
    val id: Long,
    val name: String,
    val isPrivate: Boolean
)

data class SimpleBoardDto(
    val id: Long?,
    val name: String,
    val isPrivate: Boolean
)

data class BoardDto(
    val id: Long?,
    val name: String,
    val isPrivate: Boolean,
    val posts: List<Post>
//    val posts: Set<Post>
)