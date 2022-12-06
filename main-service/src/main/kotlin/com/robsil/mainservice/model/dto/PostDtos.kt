package com.robsil.mainservice.model.dto

import com.robsil.mainservice.data.domain.Tag
import com.robsil.mainservice.model.dto.request.PostSaveRequest
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class PostCreateDto(
    @field:NotBlank
    val boardId: Long,

    @field:NotBlank
    val photoId: Long,

    @Length(min = 1, max = 128)
    val title: String?,

    @Length(min = 1, max = 1024)
    val text: String?,

    @field:NotNull
    @field:Size(min = 1)
    val tags: Set<Tag>,
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

data class ComplexPostDto(
    val id: Long,
    val title: String?,
    val text: String?,
    val photo: SimplePhotoDto,
)

data class ComplexPostPageableDto(
    val posts: List<ComplexPostDto>,
    val totalElements: Long,
    val totalPages: Int,
)
