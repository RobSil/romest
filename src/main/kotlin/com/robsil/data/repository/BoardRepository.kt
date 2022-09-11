package com.robsil.data.repository

import com.robsil.data.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<Board, Long> {

    fun findByName(name: String): Board?


}