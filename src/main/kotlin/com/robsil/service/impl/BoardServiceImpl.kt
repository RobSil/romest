package com.robsil.service.impl

import com.robsil.data.domain.Board
import com.robsil.data.domain.User
import com.robsil.data.repository.BoardRepository
import com.robsil.model.dto.BoardCreateDto
import com.robsil.model.exception.NotFoundException
import com.robsil.service.BoardService
import com.robsil.util.minimize
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
}