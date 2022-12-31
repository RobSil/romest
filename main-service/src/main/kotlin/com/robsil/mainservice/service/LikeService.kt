package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Like

interface LikeService {

    fun findAllPostIdsByUserId(userId: Long): List<Long>

    fun findByPostIdAndUserId(postId: Long, userId: Long): Like

    fun saveEntity(like: Like): Like

    fun deleteById(likeId: Long): Unit
}
