package com.robsil.mainservice.service

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.dto.BoardCreateDto
import com.robsil.mainservice.model.dto.BoardSaveDto

interface BoardService {

    fun getAll(): List<Board>

    fun getById(id: Long): Board

    fun getByName(name: String): Board

    fun getByUsernameAndMinimizedName(username: String, minimizedName: String): Board

    fun getAllForPick(userId: Long): List<Board>

    fun create(dto: BoardCreateDto, user: User): Board

    fun save(dto: BoardSaveDto, user: User): Board

    fun deleteById(boardId: Long): Unit

    fun getAllByUser(targetUser: User, requestingUser: User?): List<Board>

    fun getAllByUserId(userId: Long): List<Board>
    fun getAllByUserIdAndIsPrivate(userId: Long, isPrivate: Boolean): List<Board>
}
