package com.robsil.mainservice.service.impl.facade

import com.robsil.mainservice.model.exception.ExceptionMessages
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.service.BoardService
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.UserService
import com.robsil.mainservice.service.facade.BoardServiceFacade
import com.robsil.mainservice.service.facade.PostServiceFacade
import com.robsil.mainservice.util.compareToOrElseThrow
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class BoardServiceFacadeImpl(
    private val boardService: BoardService,
    private val userService: UserService,
    private val postService: PostService,
    private val postServiceFacade: PostServiceFacade,
) : BoardServiceFacade {

    override fun deleteById(boardId: Long, principal: Principal?) {
        val board = boardService.getById(boardId)

        val user = userService.getByPrincipal(principal)

        board.user.id.compareToOrElseThrow(user.id, ForbiddenException(ExceptionMessages.USERS_DOESNT_MATCH))

        val postsByBoard = postService.getPostsByBoard(board.id!!)

        postsByBoard.forEach { postServiceFacade.deletePost(it.id!!) }

        boardService.deleteById(boardId)
    }
}