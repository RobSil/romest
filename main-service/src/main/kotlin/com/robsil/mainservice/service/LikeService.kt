package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Like

interface LikeService {

    fun findAllPostIdsByUserId(userId: Long): List<Long>

    fun saveEntity(like: Like): Like

}