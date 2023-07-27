package com.robsil.mainservice.model.dto.request

import org.hibernate.validator.constraints.Length
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


data class PostCreateRequest(
//    @field:NotNull
    val boardId: Long,

//    @field:Length(min = 1, max = 128)
    val title: String?,

//    @field:Length(min = 1, max = 1024)
    val text: String?,

    @field:NotNull
    @field:Size(min = 1)
    val tags: Set<String>,
)

data class PostSaveRequest(
    @Length(min = 1, max = 128)
    val title: String?,

    @Length(min = 1, max = 1024)
    val text: String?,
)
