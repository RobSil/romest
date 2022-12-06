package com.robsil.mainservice.service.facade

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.data.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.security.Principal

interface BoardServiceFacade {

    fun getByUsernameAndMinimizedName(username: String, minimizedName: String): Board

    fun getAllPostsByUsernameAndMinimizedName(username: String, minimizedName: String, pageable: Pageable = Pageable.unpaged()): Page<Post>

    fun deleteById(boardId: Long, principal: Principal?): Unit

}
