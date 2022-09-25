package com.robsil.mainservice.controller

import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.model.dto.*
import com.robsil.mainservice.service.BoardService
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.UserService
import com.robsil.mainservice.service.facade.BoardServiceFacade
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.dtoFactories.toSimpleDto
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
    private val postServiceFacade: PostServiceFacade,
    private val postService: PostService,
    private val userService: UserService,
) {

    @GetMapping
    fun getAll() = boardService.getAll()

    // improve, user depends request
    @GetMapping("/{boardId}/posts")
    fun getPostsByBoard(@PathVariable boardId: Long, principal: Principal?): List<SimplePostDto> {
        val posts: List<Post> = postServiceFacade.getAllByBoardId(boardId, userService.getByPrincipal(principal))

        return posts.map { it.toSimpleDto() }
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