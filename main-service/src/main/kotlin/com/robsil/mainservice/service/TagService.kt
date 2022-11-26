package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Tag
import com.robsil.mainservice.model.dto.request.TagCreateRequest

interface TagService {

    fun getAllIdsByPostIds(postIds: List<Long>): List<Long>

    fun getByTitle(title: String): Tag

    fun create(dto: TagCreateRequest) : Tag

}