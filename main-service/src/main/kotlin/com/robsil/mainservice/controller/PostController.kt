package com.robsil.mainservice.controller

import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.model.dto.PostSaveDto
import com.robsil.mainservice.model.dto.request.PostCreateRequest
import com.robsil.mainservice.model.dto.SimplePostDto
import com.robsil.mainservice.model.dto.request.PostSaveRequest
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.dtoFactories.toSimpleDto
import org.springframework.http.HttpStatus
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
    fun getAll(): List<Post> {
        val posts: List<Post>  = postService.getAll()

        return posts
    }

    @PostMapping
    fun create(@Valid @RequestPart dto: PostCreateRequest, @RequestPart file: MultipartFile): ResponseEntity<SimplePostDto> =
        ResponseEntity(postServiceFacade.savePost(dto, file).toSimpleDto(), HttpStatus.CREATED)

    @PutMapping("/{postId}")
    fun save(@PathVariable postId: Long,
             @Valid @RequestBody dto: PostSaveRequest) =
        ResponseEntity(postService.save(PostSaveDto(postId, dto)), HttpStatus.OK)

    @DeleteMapping("/{postId}")
    fun deleteById(@PathVariable postId: Long) =
        ResponseEntity(postServiceFacade.deletePost(postId), HttpStatus.NO_CONTENT)

}