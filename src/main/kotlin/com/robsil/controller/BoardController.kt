package com.robsil.controller

import com.robsil.data.domain.Board
import com.robsil.data.domain.Post
import com.robsil.model.dto.BoardCreateDto
import com.robsil.model.dto.BoardDto
import com.robsil.model.dto.BoardSaveDto
import com.robsil.model.dto.SimpleBoardDto
import com.robsil.model.exception.ForbiddenException
import com.robsil.service.BoardService
import com.robsil.service.PostService
import com.robsil.service.UserService
import com.robsil.service.facade.BoardServiceFacade
import com.robsil.util.dtoFactories.toSimpleDto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/boards")
class BoardController(
    private val boardService: BoardService,
    private val boardServiceFacade: BoardServiceFacade,
    private val postService: PostService,
    private val userService: UserService,
) {

    @GetMapping
    fun getAll() = boardService.getAll()

    // improve, user depends request
    @GetMapping("/{boardId}/posts")
    fun getPostsByBoard(@PathVariable boardId: Long, principal: Principal?): List<Post> {
        val posts: List<Post> = postService.getPostsByBoard(boardId)

        return posts
    }

    @PostMapping
    fun create(@RequestBody dto: BoardCreateDto, principal: Principal?): SimpleBoardDto = boardService.create(dto, userService.getByPrincipal(principal)).toSimpleDto()

    @PutMapping
    fun save(@RequestBody dto: BoardSaveDto, principal: Principal?): SimpleBoardDto = boardService.save(dto, userService.getByPrincipal(principal)).toSimpleDto()

    @DeleteMapping("/{boardId}")
    fun deleteById(@PathVariable boardId: Long, principal: Principal?): Unit {
        boardServiceFacade.deleteById(boardId, principal)
    }
}