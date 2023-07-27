package com.robsil.mainservice.service.impl.facade

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.dto.SimpleBoardDto
import com.robsil.mainservice.model.exception.ExceptionMessages
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.service.BoardService
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.UserService
import com.robsil.mainservice.service.facade.BoardServiceFacade
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.compareToOrElseThrow
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.security.Principal


@Service
class BoardServiceFacadeImpl(
    private val boardService: BoardService,
    private val userService: UserService,
    private val postService: PostService,
    private val postServiceFacade: PostServiceFacade,
) : BoardServiceFacade {

    override fun getAllByUserForFront(targetUser: User, requestingUser: User?): List<SimpleBoardDto> {
        val boards = mutableListOf<SimpleBoardDto>()
        boards.addAll(boardService.getAllByUser(targetUser, requestingUser).map { it.toSimpleDto() })

        boards.add(0, SimpleBoardDto(0L, "Liked", "liked", true))
        boards.add(0, SimpleBoardDto(0L, "All Posts", "posts", true))

        return boards
    }

    override fun getByUsernameAndMinimizedName(username: String, minimizedName: String): Board {
        val board = boardService.getByUsernameAndMinimizedName(username, minimizedName)

        if (board.isPrivate) {
            val currUser = try {
                userService.getCurrentUser()
            } catch (e: NotFoundException) {
                if (e.message == "USER_NOT_FOUND") {
                    throw ForbiddenException("NOT_PERMITTED_TO_VIEW_THIS_BOARD")
                }

                throw e
            }

            if (board.user.id != currUser.id) {
                throw ForbiddenException("NOT_PERMITTED_TO_VIEW_THIS_BOARD")
            }
        }

        return board
    }

    override fun getAllPostsByUsernameAndMinimizedName(username: String, minimizedName: String, pageable: Pageable): Page<Post> {
        val board = getByUsernameAndMinimizedName(username, minimizedName)

        return postService.getAllByBoardId(board.id!!, pageable)
    }

    override fun deleteById(boardId: Long, principal: Principal?) {
        val board = boardService.getById(boardId)

        val user = userService.getByPrincipal(principal)

        board.user.id.compareToOrElseThrow(user.id, ForbiddenException(ExceptionMessages.USERS_DOESNT_MATCH))

        val postsByBoard = postService.getAllByBoardId(board.id!!)

        postsByBoard.forEach { postServiceFacade.deletePost(it.id!!) }

        boardService.deleteById(boardId)
    }
}
