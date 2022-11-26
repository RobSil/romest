package com.robsil.mainservice.data.repository

import com.robsil.mainservice.data.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardRepository: JpaRepository<Board, Long> {

    fun findByName(name: String): Board?

    @Query("select board from Board board where (board.user.username = :username and board.minimizedName = :minimizedName)")
    fun findByUsernameAndMinimizedName(username: String, minimizedName: String): Board?

    @Query("select count(board) from Board board where (board.user.username = :username and board.minimizedName = :minimizedName)")
    fun countByUsernameAndMinimizedName(username: String, minimizedName: String): Long
}