package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Like
import com.robsil.mainservice.data.repository.LikeRepository
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.service.LikeService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Service

@Service
class LikeServiceImpl(
    private val likeRepository: LikeRepository,
) : LikeService {

    private val log = logger()

    override fun findAllPostIdsByUserId(userId: Long): List<Long> {
        return likeRepository.findAllPostIdsByUserId(userId)
    }

    override fun findByPostIdAndUserId(postId: Long, userId: Long): Like {
        return likeRepository.findByPostIdAndUserId(postId, userId).orElse(null) ?: run {
            log.info("findByPostIdAndUserId: like doesn't found by postId: $postId, userId: $userId")
            throw NotFoundException("LIKE_NOT_FOUND")
        }
    }

    override fun saveEntity(like: Like): Like {
        return likeRepository.save(like)
    }

    override fun deleteById(likeId: Long) {
        likeRepository.deleteById(likeId)
    }
}
