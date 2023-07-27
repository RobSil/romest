package com.robsil.mainservice.model.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class LikePostRequest(

    @field:NotNull
    @field:Min(1)
    val postId: Long

)
