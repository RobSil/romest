package com.robsil.mainservice.service.impl.facade

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.data.domain.Tag
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.dto.ComplexPostDto
import com.robsil.mainservice.model.dto.PostCreateDto
import com.robsil.mainservice.model.dto.request.PostCreateRequest
import com.robsil.mainservice.model.dto.request.TagCreateRequest
import com.robsil.mainservice.model.exception.ExceptionMessages
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.IllegalRequestPayloadException
import com.robsil.mainservice.model.exception.InternalServerErrorException
import com.robsil.mainservice.service.*
import com.robsil.mainservice.service.facade.PhotoServiceFacade
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.compareToOrElseThrow
import org.apache.logging.log4j.kotlin.logger
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

        return ComplexPostDto(post.id!!, post.title, post.text, simplePhotoDto)
    }
    override fun getAllByBoardId(boardId: Long, user: User): List<Post> {
        val board = boardService.getById(boardId)

        if (board.isPrivate) {
            board.user.id.compareToOrElseThrow(user.id, ForbiddenException(ExceptionMessages.USERS_DOESNT_MATCH))
        }

        return postService.getAllByBoard(board)
    }

    override fun getAllByTagsRelevant(): List<Post> {
        val user = userService.getCurrentUser()

        try {
            val postIds = likeService.findAllPostIdsByUserId(user.id!!)

            if (postIds.isEmpty()) throw IllegalArgumentException("empty list of args, postIds is empty")

            val tagIds = tagService.getAllIdsByPostIds(postIds)

            if (tagIds.isEmpty()) throw IllegalArgumentException("empty list of args, tagIds is empty")

//            return postService.getAllByTagsRelevant(tagIds, user.id!!)
            return postService.getAllByTagsRelevant(tagIds)
        } catch (e: IllegalArgumentException) {
            if (e.message == "empty list of args") {
                log.info("getAllByTagsRelevant: returning empty list of args")
                return listOf()
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
