package com.robsil.mainservice.data.repository

import com.robsil.mainservice.data.domain.Like
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LikeRepository: JpaRepository<Like, Long> {

//    @Query("select p from Post p where p.board.id = :boardId")
    @Query("select l.post.id from Like l where l.user.id = :userId")
    fun findAllPostIdsByUserId(userId: Long): List<Long>

}