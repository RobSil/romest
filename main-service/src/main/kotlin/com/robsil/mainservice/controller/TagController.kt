package com.robsil.mainservice.controller

import com.robsil.mainservice.model.dto.LikeDto
import com.robsil.mainservice.model.dto.TagDto
import com.robsil.mainservice.model.dto.request.TagCreateRequest
import com.robsil.mainservice.model.exception.DataUniqueViolationException
import com.robsil.mainservice.service.TagService
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
@RequestMapping("/api/v1/tags")
class TagController(
    private val tagService: TagService,
) {

    @PostMapping
    @Operation(summary = "Create a tag.")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Successfully created tag.", content = [
            Content(mediaType = "application/json", schema = Schema(implementation = LikeDto::class))
        ]),
        ApiResponse(responseCode = "400", description = "Tag with such a title already exists.", content = [
            Content(mediaType = "application/json", schema = Schema(implementation = DataUniqueViolationException::class))
        ]),
    )
    fun create(@RequestBody @Valid dto: TagCreateRequest): ResponseEntity<TagDto> {
        return ResponseEntity(tagService.create(dto).toDto(), HttpStatus.CREATED)
    }
}
