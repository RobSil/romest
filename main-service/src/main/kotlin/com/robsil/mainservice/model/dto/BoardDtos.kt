package com.robsil.mainservice.model.dto

import com.robsil.mainservice.data.domain.Post
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

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