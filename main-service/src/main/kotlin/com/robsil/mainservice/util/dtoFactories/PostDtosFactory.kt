package com.robsil.mainservice.util.dtoFactories

import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.model.dto.SimplePostDto

class PostDtosFactory {
}

fun Post.toSimpleDto(): SimplePostDto {
    return SimplePostDto(this.id, this.title, this.text)
}