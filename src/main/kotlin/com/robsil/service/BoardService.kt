package com.robsil.service

import com.robsil.data.domain.Board
import com.robsil.data.domain.User
import com.robsil.model.dto.BoardCreateDto
import com.robsil.model.dto.BoardSaveDto

interface BoardService {

    fun getAll(): List<Board>

    fun getById(id: Long): Board

    fun getByName(name: String): Board

    fun create(dto: BoardCreateDto, user: User): Board

    fun save(dto: BoardSaveDto, user: User): Board

    fun deleteById(boardId: Long): Unit
}