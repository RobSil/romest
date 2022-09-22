package com.robsil.service.facade

import java.security.Principal

interface BoardServiceFacade {


    fun deleteById(boardId: Long, principal: Principal?): Unit

}