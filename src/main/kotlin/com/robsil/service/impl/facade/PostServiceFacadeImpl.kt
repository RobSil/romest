package com.robsil.service.impl.facade

import com.robsil.data.domain.Board
import com.robsil.data.domain.Photo
import com.robsil.data.domain.Post
import com.robsil.data.domain.User
import com.robsil.model.dto.PostCreateDto
import com.robsil.model.exception.ExceptionMessages
import com.robsil.model.exception.ForbiddenException
import com.robsil.service.BoardService
import com.robsil.service.PostService
import com.robsil.service.facade.PostServiceFacade
import com.robsil.util.compareToOrElseThrow
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

    override fun savePost(dto: PostCreateDto, multipartFile: MultipartFile): Post {
        val board: Board = boardService.getById(dto.boardId)

        var post: Post = Post(dto.title, dto.text, board)

        post = postService.saveEntity(post)

        val photo: Photo = photoServiceFacade.save(post, board.minimizedName, multipartFile)

        return post
    }
}