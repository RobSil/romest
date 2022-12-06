package com.robsil.mainservice.controller

import com.robsil.mainservice.model.dto.PostSaveDto
import com.robsil.mainservice.model.dto.request.PostCreateRequest
import com.robsil.mainservice.model.dto.SimplePostDto
import com.robsil.mainservice.model.dto.request.PostSaveRequest
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.dtoFactories.toSimpleDto
import com.robsil.mainservice.util.validators.annotations.MultipartFileImageConstraint
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postServiceFacade: PostServiceFacade,
    private val postService: PostService,
) {

    @GetMapping
    @Deprecated(message = "getAll - is intended only for testing purposes.", level = DeprecationLevel.WARNING)
    fun getAll(): List<SimplePostDto> {
        val posts = postServiceFacade.getAllByTagsRelevant().map { it.toSimpleDto() }

        return posts
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
