package com.robsil.mainservice.model.dto

data class PostCreateDto(
    val boardId: Long,
    val title: String?,
    val text: String?,
)

data class SimplePostDto(
    val id: Long?,
    val title: String?,
    val text: String?
)