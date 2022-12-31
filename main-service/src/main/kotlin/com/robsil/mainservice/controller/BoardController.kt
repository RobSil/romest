package com.robsil.mainservice.controller

import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.model.dto.*
import com.robsil.mainservice.service.BoardService
import com.robsil.mainservice.service.UserService
import com.robsil.mainservice.service.facade.BoardServiceFacade
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.dtoFactories.toSimpleDto
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/api/v1/boards")
class BoardController(
    private val boardService: BoardService,
    private val boardServiceFacade: BoardServiceFacade,
    private val postServiceFacade: PostServiceFacade,
    private val userService: UserService,
) {

    @GetMapping
    fun getAll() = boardService.getAll()

    // improve, user depends request
    @GetMapping("/{boardId}/posts")
    fun getPostsByBoard(
        @PathVariable @NotNull boardId: Long,
        principal: Principal?
    ): ResponseEntity<List<SimplePostDto>> {
        val posts: List<Post> = postServiceFacade.getAllByBoardId(boardId, userService.getByPrincipal(principal))

        return ResponseEntity(posts.map { it.toSimpleDto() }, HttpStatus.OK)
    }

    @GetMapping("/byUsernameAndMinimizedName")
    fun getByUsernameAndMinimizedName(
        @RequestParam username: String,
        @RequestParam minimizedName: String,
        principal: Principal?
    ): ResponseEntity<SimpleBoardDto> {
        return ResponseEntity(
            boardService.getByUsernameAndMinimizedName(username, minimizedName).toSimpleDto(),
            HttpStatus.OK
        )
    }

    @GetMapping("/pick")
    fun getBoardsToPick(principal: Principal?): ResponseEntity<List<BoardPickDto>> {
        return ResponseEntity(
            boardService.getAllForPick(userService.getByPrincipal(principal).id!!).map { it.toPickDto() },
            HttpStatus.OK
        )
    }

    @GetMapping("/byUsernameAndMinimizedName/posts")
    fun getAllByUsernameAndBoardMinimizedName(
        @RequestParam username: String,
        @RequestParam minimizedName: String,
        @RequestParam(required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam(required = false, defaultValue = "20") pageSize: Int,
    ): ResponseEntity<ComplexPostPageableDto> {
        val posts = boardServiceFacade.getAllPostsByUsernameAndMinimizedName(
            username,
            minimizedName,
            PageRequest.of(pageNumber, pageSize)
        ).map { postServiceFacade.toComplexPostDto(it) }
        return ResponseEntity(
            ComplexPostPageableDto(
                posts.toList(),
                posts.totalElements,
                posts.totalPages
            ), HttpStatus.OK
        )
    }
//    fun get

    @PostMapping
    fun create(@RequestBody dto: BoardCreateDto, principal: Principal?): ResponseEntity<CompleteBoardDto> =
        ResponseEntity(
            boardService.create(dto, userService.getByPrincipal(principal)).toCompleteDto(),
            HttpStatus.CREATED
        )

    @PutMapping
    fun save(@RequestBody dto: BoardSaveDto, principal: Principal?): ResponseEntity<CompleteBoardDto> =
        ResponseEntity(boardService.save(dto, userService.getByPrincipal(principal)).toCompleteDto(), HttpStatus.OK)

    @DeleteMapping("/{boardId}")
    fun deleteById(@PathVariable boardId: Long, principal: Principal?) {
        boardServiceFacade.deleteById(boardId, principal)
        ResponseEntity<Unit>(HttpStatus.NO_CONTENT)
    }
}
