package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.data.repository.PostRepository
import com.robsil.mainservice.model.dto.PostCreateDto
import com.robsil.mainservice.service.BoardService
import com.robsil.mainservice.service.PostService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val boardService: BoardService,
) : PostService {

    override fun getAll(): List<Post> {
        return postRepository.findAll()
    }

    override fun getPostsByBoard(boardId: Long): List<Post> {
        val board = boardService.getById(boardId)

        return postRepository.findAllByBoard(board)
    }

    override fun getPostsByBoardId(boardId: Long): List<Post> {
        return postRepository.findAllByBoardId(boardId)
    }

    override fun saveEntity(post: Post): Post {
        return postRepository.save(post)
    }

    override fun create(dto: PostCreateDto): Post {
        TODO("Not yet implemented")
    }

    override fun deleteById(postId: Long) {
        TODO("Not yet implemented")
    }
}