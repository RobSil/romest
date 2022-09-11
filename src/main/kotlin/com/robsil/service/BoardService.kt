package com.robsil.service

import com.robsil.data.domain.Board
import com.robsil.model.dto.BoardCreateDto

interface BoardService {

    fun getAll(): List<Board>

    fun getById(id: Long): Board

    fun getByName(name: String): Board

    fun create(dto: BoardCreateDto): Board
}