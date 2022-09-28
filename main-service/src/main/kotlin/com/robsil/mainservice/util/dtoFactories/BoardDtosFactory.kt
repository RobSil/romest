package com.robsil.mainservice.util.dtoFactories

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.model.dto.BoardDto
import com.robsil.mainservice.model.dto.SimpleBoardDto

class BoardDtosFactory {
}

fun Board.toSimpleDto(): SimpleBoardDto {
    return SimpleBoardDto(this.id, this.name, this.isPrivate)
//    return if (this.id != null) SimpleBoardDto(this.id, this.name, this.isPrivate)
//    else SimpleBoardDto(-1L, this.name, this.isPrivate)
}

//fun Board.toDto(): BoardDto {
//    return if (this.id != null) BoardDto(this.id, this.name, this.isPrivate, this.posts)
//    else BoardDto(-1L, this.name, this.isPrivate, this.posts)
//}

fun Board.toDto(): BoardDto {
    return BoardDto(this.id, this.name, this.isPrivate, listOf())
//    return if (this.id != null) BoardDto(this.id, this.name, this.isPrivate, listOf())
//    else BoardDto(-1L, this.name, this.isPrivate, listOf())
}