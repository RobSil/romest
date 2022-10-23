package com.robsil.mainservice.model.dto

import com.robsil.mainservice.model.dto.request.PostSaveRequest
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank

data class PostCreateDto(
    @field:NotBlank
    val boardId: Long,

    @field:NotBlank
    val photoId: Long,

    @Length(min = 1, max = 128)
    val title: String?,

    @Length(min = 1, max = 1024)
    val text: String?,
)

data class PostSaveDto(
    @field:NotBlank
    val id: Long,

    @Length(min = 1, max = 128)
    val title: String?,

    @Length(min = 1, max = 1024)
    val text: String?,
) {
    constructor(id: Long, dto: PostSaveRequest) : this(id, dto.title, dto.text)
}

data class SimplePostDto(
    val id: Long?,
    val title: String?,
    val text: String?
)