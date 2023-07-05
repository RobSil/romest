package com.robsil.mainservice.model.dto

import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.model.UserSimpleDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

data class BoardCreateDto(
    @NotBlank
    @Length(min = 3, max = 64)
    val name: String,

    @NotNull
    val isPrivate: Boolean
)

data class BoardSaveDto(
    @NotBlank
    val id: Long,

    @NotBlank
    @Length(min = 3, max = 64)
    val name: String,
    val isPrivate: Boolean
)

data class CompleteBoardDto(
    val id: Long?,
    val name: String,
    val minimizedName: String,
    val isPrivate: Boolean,
    val user: UserSimpleDto
)

data class ComplexBoardWithPhotosDto(
    val id: Long?,
    val name: String,
    val minimizedName: String,
    val isPrivate: Boolean,
    val user: UserSimpleDto
)
data class SimpleBoardDto(
    val id: Long?,
    val name: String,
    val minimizedName: String,
    val isPrivate: Boolean
)

data class BoardPickDto(
    val id: Long?,
    val name: String,
    val isPrivate: Boolean,
)

data class BoardDto(
    val id: Long?,
    val name: String,
    val isPrivate: Boolean,
    val posts: List<Post>
//    val posts: Set<Post>
)
