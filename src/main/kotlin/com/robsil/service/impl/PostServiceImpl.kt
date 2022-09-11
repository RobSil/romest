package com.robsil.service.impl

import com.robsil.data.domain.BaseEntity
import com.robsil.data.domain.Board
import com.robsil.data.domain.Post
import com.robsil.data.repository.BoardRepository
import com.robsil.data.repository.PostRepository
import com.robsil.model.dto.PostCreateDto
import com.robsil.service.BoardService
import com.robsil.service.PostService
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val boardService: BoardService,
) : PostService {

    override fun getPostsByBoard(boardId: Long): List<Post> {
//        val board: Board = boardService.getById(boardId)
        val board: Board = Board(boardId, "", true)

        return postRepository.findAllByBoard(board)
    }

    override fun saveEntity(post: Post): Post {
        return postRepository.save(post)
    }

    override fun create(dto: PostCreateDto): Post {
        TODO("Not yet implemented")
    }
}