package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.data.repository.BoardRepository
import com.robsil.mainservice.model.dto.BoardCreateDto
import com.robsil.mainservice.model.dto.BoardSaveDto
import com.robsil.mainservice.model.exception.ExceptionMessages.USERS_DOESNT_MATCH
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.IllegalRequestPayloadException
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

    val RESERVED_NAMES = mutableListOf<String>("pins", "liked")

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

    override fun getByUsernameAndMinimizedName(username: String, minimizedName: String): Board {
        return boardRepository.findByUsernameAndMinimizedName(username, minimizedName) ?: run {
            log.info("getByName: can't find board with username: $username, minimizedName: $minimizedName")
            throw NotFoundException("BOARD_NOT_FOUND")
        }
    }

    override fun getAllByUserId(userId: Long): List<Board> {
        return boardRepository.findAllByUserId(userId)
    }

    override fun getAllByUserIdAndIsPrivate(userId: Long, isPrivate: Boolean): List<Board> {
        return boardRepository.findAllByUserIdAndIsPrivate(userId, isPrivate)
    }

    override fun getAllByUser(targetUser: User, requestingUser: User?): List<Board> {
        return if (requestingUser == null || requestingUser.id != targetUser.id) {
            getAllByUserIdAndIsPrivate(targetUser.id!!, false)
        } else {
            getAllByUserId(targetUser.id!!)
        }
    }

    override fun getAllForPick(userId: Long): List<Board> {
        return boardRepository.findAllByUserId(userId)
    }

    fun countByUsernameAndMinimizedName(username: String, minimizedName: String): Long {
        return boardRepository.countByUsernameAndMinimizedName(username, minimizedName)
    }

    fun isExistsByUsernameAndMinimizedName(username: String, minimizedName: String): Boolean {
        return countByUsernameAndMinimizedName(username, minimizedName) > 0
    }

    fun saveEntity(board: Board): Board {
        return boardRepository.save(board)
    }

    override fun create(dto: BoardCreateDto, user: User): Board {
        var board = Board(dto.name, dto.name.minimize(), dto.isPrivate, user)

        if (isExistsByUsernameAndMinimizedName(user.username, board.minimizedName)) {
            throw IllegalRequestPayloadException("Board with similar name is already exists.")
        }

        validateMinimizedName(board.minimizedName)

        board = saveEntity(board)

        return board
    }

    override fun save(dto: BoardSaveDto, user: User): Board {
        var board = getById(dto.id)

        board.user.id.compareToOrElseThrow(user.id, ForbiddenException(USERS_DOESNT_MATCH))

        val minimizedName = dto.name.minimize()

        if (board.name != dto.name && isExistsByUsernameAndMinimizedName(user.username, minimizedName)) {
            throw IllegalRequestPayloadException("Board with similar name is already exists.")
        }

        validateMinimizedName(minimizedName)

        board.apply {
            name = dto.name
            this.minimizedName = minimizedName
            isPrivate = dto.isPrivate
        }

        board = saveEntity(board)

        return board
    }

    fun validateMinimizedName(minimizedName: String): Unit {
        if (RESERVED_NAMES.contains(minimizedName)) {
            throw IllegalRequestPayloadException("Board can't be a reserved name.")
        }
    }

    override fun deleteById(boardId: Long) {
        // implement deleting posts?
        boardRepository.deleteById(boardId)
    }
}
