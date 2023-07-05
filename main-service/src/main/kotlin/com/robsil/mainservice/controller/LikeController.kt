package com.robsil.mainservice.controller

import com.robsil.mainservice.model.dto.LikeDto
import com.robsil.mainservice.model.dto.SimplePostDto
import com.robsil.mainservice.model.dto.request.LikePostRequest
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.service.LikeService
import com.robsil.mainservice.service.facade.LikeServiceFacade
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/likes")
class LikeController(
    private val likeService: LikeService,
    private val likeServiceFacade: LikeServiceFacade,
) {

    @PostMapping
    @Operation(summary = "Like a post")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Successfully liked.", content = [
            Content(mediaType = "application/json", schema = Schema(implementation = LikeDto::class))
        ]),
        ApiResponse(responseCode = "404", description = "Post doesn't exists with such an id.", content = [
            Content(mediaType = "application/json", schema = Schema(implementation = NotFoundException::class))
        ]),
        ApiResponse(responseCode = "404", description = "User not found.", content = [
            Content(mediaType = "application/json", schema = Schema(implementation = NotFoundException::class))
        ]),
    )
    fun likePost(@RequestBody @Valid dto: LikePostRequest): ResponseEntity<LikeDto> {
        return ResponseEntity(likeServiceFacade.likePost(dto).toDto(), HttpStatus.CREATED)
    }
}
