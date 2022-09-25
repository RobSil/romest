package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.data.repository.BoardRepository
import com.robsil.mainservice.model.dto.BoardCreateDto
import com.robsil.mainservice.model.dto.BoardSaveDto
import com.robsil.mainservice.model.exception.ExceptionMessages.USERS_DOESNT_MATCH
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.service.BoardService
import com.robsil.mainservice.util.compareToOrElseThrow
import com.robsil.mainservice.util.minimize
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BoardServiceImpl(
    private val boardRepository: BoardRepository
) : BoardService {

    private val log = logger()

    override fun getAll(): List<Board> {
        return boardRepository.findAll()
    }

    override fun getById(id: Long): Board {
        return boardRepository.findById(id).orElse(null)
            ?: run {
                log.info("getById: board not found. ID: $id")
                throw NotFoundException("BOARD_NOT_FOUND")
            }
    }

    override fun getByName(name: String): Board {
        return boardRepository.findByName(name) ?: run {
            log.info("getByName: can't find board with name: $name")
            throw NotFoundException("BOARD_NOT_FOUND")
        }
    }

    fun saveEntity(board: Board): Board {
        return boardRepository.save(board)
    }

    override fun create(dto: BoardCreateDto, user: User): Board {
        var board: Board = Board(dto.name, dto.name.minimize(), dto.isPrivate, user)

        board = saveEntity(board)

        return saveEntity(board)
    }

    override fun save(dto: BoardSaveDto, user: User): Board {
        var board = getById(dto.id)

        board.user.id.compareToOrElseThrow(user.id, ForbiddenException(USERS_DOESNT_MATCH))

        board.apply {
            name = dto.name
            isPrivate = dto.isPrivate
        }

        board = saveEntity(board)

        return board
    }

    override fun deleteById(boardId: Long) {
        // implement deleting posts?
        boardRepository.deleteById(boardId)
    }
}