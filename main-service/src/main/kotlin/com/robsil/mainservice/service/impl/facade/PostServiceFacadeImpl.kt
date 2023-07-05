package com.robsil.mainservice.service.impl.facade

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.data.domain.Tag
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.ComplexUserDto
import com.robsil.mainservice.model.dto.ComplexPostDto
import com.robsil.mainservice.model.dto.PostCreateDto
import com.robsil.mainservice.model.dto.SimplePhotoDto
import com.robsil.mainservice.model.dto.request.PostCreateRequest
import com.robsil.mainservice.model.dto.request.TagCreateRequest
import com.robsil.mainservice.model.enum.ERole
import com.robsil.mainservice.model.enum.StoringSource
import com.robsil.mainservice.model.exception.ExceptionMessages
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.IllegalRequestPayloadException
import com.robsil.mainservice.model.exception.InternalServerErrorException
import com.robsil.mainservice.service.*
import com.robsil.mainservice.service.facade.PhotoServiceFacade
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.compareToOrElseThrow
import org.apache.logging.log4j.kotlin.logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class PostServiceFacadeImpl(
    private val postService: PostService,
//    private val photoService: PhotoService,
    private val photoServiceFacade: PhotoServiceFacade,
    private val boardService: BoardService,
    private val userService: UserService,
    private val likeService: LikeService,
    private val tagService: TagService,
) : PostServiceFacade {

    private val log = logger()

    override fun toComplexPostDto(post: Post): ComplexPostDto {
        val simplePhotoDto = photoServiceFacade.toSimplePhotoDto(post.photo)

        val like = try {
            val user = userService.getCurrentUser()
            likeService.findByPostIdAndUserId(post.id!!, user.id!!)
        } catch (_: Exception) {
            null
        }

        return ComplexPostDto(post.id!!, post.title, post.text, like != null, simplePhotoDto, ComplexUserDto(post.board.user.username, SimplePhotoDto("", StoringSource.IMAGEKIT, "", "", ByteArray(0))))
    }
    override fun getAllByBoardId(boardId: Long, user: User): List<Post> {
        val board = boardService.getById(boardId)

        if (board.isPrivate) {
            board.user.id.compareToOrElseThrow(user.id, ForbiddenException(ExceptionMessages.USERS_DOESNT_MATCH))
        }

        return postService.getAllByBoard(board)
    }

    override fun repinPost(postId: Long, boardId: Long, user: User): Post {
        val board = boardService.getById(boardId)

        if (board.user.id != user.id &&
            !user.roles.any { role -> role.title == ERole.ADMIN.title || role.title == ERole.SUPERADMIN.title }) {
            throw ForbiddenException(ExceptionMessages.FORBIDDEN_BOARD_FOR_USER)
        }

        val post = postService.getById(postId)

        if (post.board.user.id != user.id &&
            post.board.isPrivate &&
            !user.roles.any { role -> role.title == ERole.ADMIN.title || role.title == ERole.SUPERADMIN.title }) {
            throw ForbiddenException(ExceptionMessages.FORBIDDEN_BOARD_FOR_USER)
        }

        val newPost = postService.create(PostCreateDto(board.id!!, post.photo.id!!, post.title, post.text, post.tags))

        return newPost
    }

    override fun getAllByTagsRelevant(user: User, pageable: Pageable): Page<Post> {

        try {
            val likedPostIds = likeService.findAllPostIdsByUserId(user.id!!)

//            if (likedPostIds.isEmpty()) throw IllegalArgumentException("empty list of args, postIds is empty")

            val likedTagIds = tagService.getAllIdsByPostIds(likedPostIds)

//            if (likedTagIds.isEmpty()) throw IllegalArgumentException("empty list of args, tagIds is empty")

//            return postService.getAllByTagsRelevant(tagIds, user.id!!)
            return postService.getAllByTagsRelevant(likedTagIds, pageable)
        } catch (e: IllegalArgumentException) {
            if (e.message == "empty list of args") {
                log.info("getAllByTagsRelevant: returning empty list of args")
                return Page.empty()
            }

            throw e
        }

    }

    override fun savePost(dto: PostCreateRequest, multipartFile: MultipartFile): Post {
        val board: Board = boardService.getById(dto.boardId)

        val photo = photoServiceFacade.save(board.minimizedName, multipartFile)

//        var post = Post(dto.title, dto.text, board, photo)

        val tags = mutableSetOf<Tag>()

        dto.tags.forEach {
            try {
                tags.add(tagService.getByTitle(it))
            } catch (_: Exception) {
                try {
                    tags.add(tagService.create(TagCreateRequest(it)))
                    log.info("savePost: tag created for new post")
                } catch (_: Exception) {
                    log.error("savePost: unexpected error occurred!")
                }
            }
        }

        if (tags.isEmpty()) {
            throw IllegalRequestPayloadException("Couldn't get any tag for a post.")
        }

        val post = try {
            postService.create(PostCreateDto(board.id!!, photo.id!!, dto.title, dto.text, tags))
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
