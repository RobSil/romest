package com.robsil.mainservice.controller

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.service.BoardService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class BoardGraphqlController(
    private val boardService: BoardService
){

    @QueryMapping
    fun boards(): List<Board> {
        return boardService.getAll()
    }

    @QueryMapping
    fun boardByUsernameAndMinimizedName(@Argument username: String, @Argument minimizedName: String): Board {
        return boardService.getByUsernameAndMinimizedName(username, minimizedName)
    }
}