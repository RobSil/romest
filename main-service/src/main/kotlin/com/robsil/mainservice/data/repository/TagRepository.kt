package com.robsil.mainservice.data.repository

import com.robsil.mainservice.data.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TagRepository: JpaRepository<Tag, Long> {

    @Query("select distinct tag.id from Tag tag inner join tag.posts post where post.id in :postIds")
    fun findAllIdsByPostIds(postIds: List<Long>): List<Long>

    @Query("select distinct tag from Tag tag where tag.title = :title")
    fun findByTitle(title: String): Tag?
}