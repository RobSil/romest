package com.robsil.mainservice.model.dto.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


data class PostCreateRequest(
    @field:NotNull
    val boardId: Long,

    @Length(min = 1, max = 128)
    val title: String?,

    @Length(min = 1, max = 1024)
    val text: String?,
)

data class PostSaveRequest(
    @Length(min = 1, max = 128)
    val title: String?,

    @Length(min = 1, max = 1024)
    val text: String?,
)