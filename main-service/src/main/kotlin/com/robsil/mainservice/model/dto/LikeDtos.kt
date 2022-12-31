package com.robsil.mainservice.model.dto

import com.robsil.mainservice.model.UserInformationDto

data class LikeDto(
    val user: UserInformationDto,
    val post: SimplePostDto
)

data class LikeResponse(
    val isLiked: Boolean,
)
