package com.robsil.mainservice.data.repository

import com.robsil.mainservice.data.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository: JpaRepository<Board, Long> {

    fun findByName(name: String): Board?


}