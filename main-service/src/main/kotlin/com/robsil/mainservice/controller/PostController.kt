package com.robsil.mainservice.controller

import com.robsil.mainservice.model.dto.*
import com.robsil.mainservice.model.dto.request.PostCreateRequest
import com.robsil.mainservice.model.dto.request.LikePostRequest
import com.robsil.mainservice.model.dto.request.PostSaveRequest
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.UserService
import com.robsil.mainservice.service.facade.LikeServiceFacade
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.dtoFactories.toSimpleDto
import com.robsil.mainservice.util.validators.annotations.MultipartFileImageConstraint
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postServiceFacade: PostServiceFacade,
    private val likeServiceFacade: LikeServiceFacade,
    private val postService: PostService,
    private val userService: UserService,
) {

    @GetMapping("/{postId}")
    fun getById(@PathVariable postId: Long): ResponseEntity<ComplexPostDto> {
        return ResponseEntity(postServiceFacade.toComplexPostDto(postService.getById(postId)), HttpStatus.OK)
    }

    @PostMapping("/{postId}/repin")
    fun repinPost(
        @PathVariable postId: Long,
        @RequestParam boardId: Long,
        principal: Principal?
    ): ResponseEntity<SimplePostDto> {
        return ResponseEntity(
            postServiceFacade.repinPost(postId, boardId, userService.getByPrincipal(principal)).toSimpleDto(),
            HttpStatus.OK
        )
    }

    @PostMapping("/{postId}/toggleLike")
    fun likePost(@PathVariable postId: Long): ResponseEntity<LikeResponse> {
        return ResponseEntity(likeServiceFacade.toggleLike(LikePostRequest(postId)), HttpStatus.OK)
    }


    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Create a post")
    @ApiResponses(
        ApiResponse(
            responseCode = "201", description = "Successfully created.", content = [
                Content(mediaType = "application/json", schema = Schema(implementation = SimplePostDto::class))
            ]
        ),
        ApiResponse(
            responseCode = "403", description = "User doesn't match with board owner.", content = [
                Content(mediaType = "application/json", schema = Schema(implementation = ForbiddenException::class))
            ]
        ),
        ApiResponse(
            responseCode = "404", description = "Board not found.", content = [
                Content(mediaType = "application/json", schema = Schema(implementation = NotFoundException::class))
            ]
        ),
    )
    fun create(
        @Valid @RequestPart dto: PostCreateRequest,
        @MultipartFileImageConstraint @RequestPart file: MultipartFile
    ): ResponseEntity<SimplePostDto> =
        ResponseEntity(postServiceFacade.savePost(dto, file).toSimpleDto(), HttpStatus.CREATED)

    @PutMapping("/{postId}")
    fun save(
        @PathVariable postId: Long,
        @Valid @RequestBody dto: PostSaveRequest
    ) =
        ResponseEntity(postService.save(PostSaveDto(postId, dto)).toSimpleDto(), HttpStatus.OK)

    @DeleteMapping("/{postId}")
    fun deleteById(@PathVariable postId: Long) =
        ResponseEntity(postServiceFacade.deletePost(postId), HttpStatus.NO_CONTENT)

}
