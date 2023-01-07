package com.robsil.mainservice.service.facade

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.Post
import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.dto.SimpleBoardDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.security.Principal

interface BoardServiceFacade {

    fun getAllByUserForFront(targetUser: User, requestingUser: User?): List<SimpleBoardDto>

    fun getByUsernameAndMinimizedName(username: String, minimizedName: String): Board

    fun getAllPostsByUsernameAndMinimizedName(username: String, minimizedName: String, pageable: Pageable = Pageable.unpaged()): Page<Post>

    fun deleteById(boardId: Long, principal: Principal?): Unit

}
