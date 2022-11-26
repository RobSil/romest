package com.robsil.mainservice.model.dto.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class LikePostRequest(

    @field:NotNull
    @field:Min(1)
    val postId: Long

)