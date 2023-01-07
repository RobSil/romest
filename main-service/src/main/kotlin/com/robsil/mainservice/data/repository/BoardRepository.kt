package com.robsil.mainservice.data.repository

import com.robsil.mainservice.data.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BoardRepository: JpaRepository<Board, Long> {

    @Query("select b from Board b where b.user.id = :userId and b.isPrivate = :isPrivate")
    fun findAllByUserIdAndIsPrivate(userId: Long, isPrivate: Boolean): List<Board>

    fun findByName(name: String): Board?

    @Query("select board from Board board where board.user.id = :userId")
    fun findAllByUserId(userId: Long): List<Board>

    @Query("select board from Board board where (board.user.username = :username and board.minimizedName = :minimizedName)")
    fun findByUsernameAndMinimizedName(username: String, minimizedName: String): Board?

    @Query("select count(board) from Board board where (board.user.username = :username and board.minimizedName = :minimizedName)")
    fun countByUsernameAndMinimizedName(username: String, minimizedName: String): Long
}
