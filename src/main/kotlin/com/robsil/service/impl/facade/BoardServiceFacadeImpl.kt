package com.robsil.service.impl.facade

import com.robsil.model.exception.ExceptionMessages
import com.robsil.model.exception.ForbiddenException
import com.robsil.model.exception.InternalServerErrorException
import com.robsil.service.BoardService
import com.robsil.service.PostService
import com.robsil.service.UserService
import com.robsil.service.facade.BoardServiceFacade
import com.robsil.util.compareToOrElseThrow
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException.InternalServerError
import java.security.Principal

@Service
class BoardServiceFacadeImpl(
    private val boardService: BoardService,
    private val userService: UserService,
    private val postService: PostService,
) : BoardServiceFacade {

    override fun deleteById(boardId: Long, principal: Principal?) {
        val board = boardService.getById(boardId)

        val user = userService.getByPrincipal(principal)

        board.user.id.compareToOrElseThrow(user.id, ForbiddenException(ExceptionMessages.USERS_DOESNT_MATCH))

        val postsByBoard = postService.getPostsByBoard(board.id!!)

        postsByBoard.forEach { postService.deleteById(it.id!!) }

        boardService.deleteById(boardId)
    }
}