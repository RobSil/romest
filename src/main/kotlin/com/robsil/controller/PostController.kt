package com.robsil.controller

import com.robsil.data.domain.Post
import com.robsil.model.dto.PostCreateDto
import com.robsil.service.PostService
import com.robsil.service.facade.PostServiceFacade
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
    fun savePost(@RequestPart dto: PostCreateDto, @RequestPart file: MultipartFile) = postServiceFacade.savePost(dto, file)
}