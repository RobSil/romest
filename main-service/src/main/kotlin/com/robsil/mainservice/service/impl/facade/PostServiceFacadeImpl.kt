package com.robsil.mainservice.service.impl.facade

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.dto.PostCreateDto
import com.robsil.mainservice.model.dto.request.PostCreateRequest
import com.robsil.mainservice.model.exception.ExceptionMessages
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.InternalServerErrorException
import com.robsil.mainservice.service.BoardService
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.compareToOrElseThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class PostServiceFacadeImpl(
    private val postService: PostService,
//    private val photoService: PhotoService,
    private val photoServiceFacade: PhotoServiceFacadeImpl,
    private val boardService: BoardService
) : PostServiceFacade {
    override fun getAllByBoardId(boardId: Long, user: User): List<Post> {
        val board = boardService.getById(boardId)

        if (board.isPrivate) {
            board.user.id.compareToOrElseThrow(user.id, ForbiddenException(ExceptionMessages.USERS_DOESNT_MATCH))
        }

        return postService.getPostsByBoardId(board.id!!)
    }

    override fun savePost(dto: PostCreateRequest, multipartFile: MultipartFile): Post {
        val board: Board = boardService.getById(dto.boardId)

        val photo = photoServiceFacade.save(board.minimizedName, multipartFile)

//        var post = Post(dto.title, dto.text, board, photo)

        val post = try {
            postService.create(PostCreateDto(board.id!!, photo.id!!, dto.title, dto.text))
        } catch (e: Exception) {
            photoServiceFacade.deleteById(photo.id!!)
            throw InternalServerErrorException("Exception occurred during saving post.", e)
        }

        return post
    }

    override fun deletePost(postId: Long) {
        val post = postService.getById(postId)

        postService.deleteById(post.id!!)

        photoServiceFacade.deleteById(post.photo.id!!)
    }
}