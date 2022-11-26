package com.robsil.mainservice.service.facade

import com.robsil.mainservice.data.domain.Like
import com.robsil.mainservice.model.dto.request.LikePostRequest

interface LikeServiceFacade {

    fun likePost(dto: LikePostRequest): Like
}