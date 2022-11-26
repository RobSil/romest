package com.robsil.mainservice.service.impl.facade

import com.robsil.mainservice.data.domain.Like
import com.robsil.mainservice.model.dto.request.LikePostRequest
import com.robsil.mainservice.service.LikeService
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.UserService
import com.robsil.mainservice.service.facade.LikeServiceFacade
import org.springframework.stereotype.Service

@Service
class LikeServiceFacadeImpl(
    private val likeService: LikeService,
    private val postService: PostService,
    private val userService: UserService
) : LikeServiceFacade {

    override fun likePost(dto: LikePostRequest): Like {
        val post = postService.getById(dto.postId)

        val user = userService.getCurrentUser()

        val like = Like(user, post)

        return likeService.saveEntity(like)
    }
}