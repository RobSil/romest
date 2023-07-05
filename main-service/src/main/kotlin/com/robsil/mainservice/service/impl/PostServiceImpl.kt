package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.Photo
import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.data.repository.PostRepository
import com.robsil.mainservice.model.dto.PostCreateDto
import com.robsil.mainservice.model.dto.PostSaveDto
import com.robsil.mainservice.model.enum.ERole
import com.robsil.mainservice.model.exception.ExceptionMessages.FORBIDDEN_BOARD_FOR_USER
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.model.exception.constant.PostExceptionMessages
import com.robsil.mainservice.service.BoardService
import com.robsil.mainservice.service.PhotoService
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.UserService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val boardService: BoardService,
    private val photoService: PhotoService,
    private val userService: UserService,
) : PostService {


    val log = logger()

    @Deprecated("getAll - is intended only for testing purposes.", level = DeprecationLevel.WARNING)
    override fun getAll(): List<Post> {
        return postRepository.findAll()
    }

    override fun getById(id: Long): Post {
        return postRepository.findById(id).orElse(null)
            ?: run {
                log.info("getById: post not found. ID: $id")
                throw NotFoundException(PostExceptionMessages.POST_NOT_FOUND)
            }
    }

    override fun getAllByBoardId(boardId: Long): List<Post> {
        val board = boardService.getById(boardId)

        return postRepository.findAllByBoard(board)
    }

    override fun getAllByBoard(board: Board): List<Post> {
        return postRepository.findAllByBoardId(board.id!!)
    }

    override fun getAllByBoardId(boardId: Long, pageable: Pageable): Page<Post> {
        return postRepository.findAllByBoardIdPageable(boardId, pageable)
    }

    override fun getAllByTagsRelevant(tags: List<Long>, userId: Long, pageable: Pageable): Page<Post> {
        return postRepository.findAllByTagsRelevant(tags, userId, pageable)
    }

    override fun getAllByTagsRelevant(tags: List<Long>, pageable: Pageable): Page<Post> {
        return postRepository.findAllByTagsRelevant(tags, pageable)
    }

    override fun getAllByUsername(username: String, pageable: Pageable): Page<Post> {
        return postRepository.findAllByUsername(username, pageable)
    }

    override fun getAllLikedByUsername(username: String, pageable: Pageable): Page<Post> {
        return postRepository.findAllLikedByUsername(username, pageable)
    }

    override fun saveEntity(post: Post): Post {
        return postRepository.save(post)
    }

    override fun create(dto: PostCreateDto): Post {
        val board: Board = boardService.getById(dto.boardId)

        verifyBoardUser(board, userService.getCurrentUser())

        val photo: Photo = photoService.getById(dto.photoId)

        var post = Post(dto.title, dto.text, board, photo)

        post.tags.addAll(dto.tags)

        post = saveEntity(post)

        return post
    }

    override fun save(dto: PostSaveDto): Post {
        var post = getById(dto.id)

        verifyBoardUser(post.board, userService.getCurrentUser())

        post.apply {
            title = dto.title
            text = dto.text
        }

        post = saveEntity(post)

        return post
    }

    override fun deleteById(postId: Long) {
        val post: Post = try {
            getById(postId)
        } catch (_: Exception) {
            return
        }

        verifyBoardUser(post.board, userService.getCurrentUser())

        postRepository.deleteById(post.id!!)
    }

    private fun verifyBoardUser(board: Board, currentUser: User) {
        if (board.user.id != currentUser.id &&
            !currentUser.roles.any { role -> role.title == ERole.ADMIN.title || role.title == ERole.SUPERADMIN.title }) {
            throw ForbiddenException(FORBIDDEN_BOARD_FOR_USER)
        }
    }
}
