package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Like
import com.robsil.mainservice.data.repository.LikeRepository
import com.robsil.mainservice.service.LikeService
import org.springframework.stereotype.Service

@Service
class LikeServiceImpl(
    private val likeRepository: LikeRepository,
) : LikeService {

    override fun findAllPostIdsByUserId(userId: Long): List<Long> {
        return likeRepository.findAllPostIdsByUserId(userId)
    }

    override fun saveEntity(like: Like): Like {
        return likeRepository.save(like)
    }

}