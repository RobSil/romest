package com.robsil.mainservice.controller

import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.model.dto.PostCreateDto
import com.robsil.mainservice.model.dto.SimplePostDto
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.dtoFactories.toSimpleDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postServiceFacade: PostServiceFacade,
    private val postService: PostService,
) {

    @GetMapping
    fun getAll(): List<Post> {
        val posts: List<Post>  = postService.getAll()

        return posts
    }

    @PostMapping
    fun savePost(@RequestPart dto: PostCreateDto, @RequestPart file: MultipartFile): SimplePostDto = postServiceFacade.savePost(dto, file).toSimpleDto()
}