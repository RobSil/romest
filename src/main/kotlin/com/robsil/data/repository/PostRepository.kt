package com.robsil.data.repository

import com.robsil.data.domain.BaseEntity
import com.robsil.data.domain.Board
import com.robsil.data.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {

    fun findAllByBoard(board: Board): List<Post>

}