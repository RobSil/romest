package com.robsil.util.dtoFactories

import com.robsil.data.domain.Post
import com.robsil.model.dto.SimplePostDto

class PostDtosFactory {
}

fun Post.toSimpleDto(): SimplePostDto {
    return SimplePostDto(this.id, this.title, this.text)
}