package com.robsil.mainservice.model.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class LikePostRequest(

    @field:NotNull
    @field:Min(1)
    val postId: Long

)
