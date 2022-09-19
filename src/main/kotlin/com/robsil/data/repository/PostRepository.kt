package com.robsil.data.repository

import com.robsil.data.domain.BaseEntity
import com.robsil.data.domain.Board
import com.robsil.data.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostRepository: JpaRepository<Post, Long> {

    fun findAllByBoard(board: Board): List<Post>

    @Query("select p from Post p where p.board.id = :boardId")
    fun findAllByBoardId(boardId: Long): List<Post>

}