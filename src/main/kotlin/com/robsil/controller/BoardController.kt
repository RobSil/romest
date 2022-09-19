package com.robsil.controller

import com.robsil.data.domain.Board
import com.robsil.data.domain.Post
import com.robsil.model.dto.BoardCreateDto
import com.robsil.model.dto.BoardDto
import com.robsil.model.dto.SimpleBoardDto
import com.robsil.service.BoardService
import com.robsil.service.PostService
import com.robsil.service.UserService
import com.robsil.util.toDto
import com.robsil.util.toSimpleDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/boards")
class BoardController(
    private val boardService: BoardService,
    private val postService: PostService,
    private val userService: UserService,
) {

    @GetMapping
    fun getAll() = boardService.getAll()

    @GetMapping("/{boardId}/posts")
    fun getPostsByBoard(@PathVariable boardId: Long): List<Post> {
        val posts: List<Post> = postService.getPostsByBoard(boardId)

        return posts
    }

    @PostMapping
    fun create(@RequestBody dto: BoardCreateDto, principal: Principal?): SimpleBoardDto = boardService.create(dto, userService.getByPrincipal(principal)).toSimpleDto()
}