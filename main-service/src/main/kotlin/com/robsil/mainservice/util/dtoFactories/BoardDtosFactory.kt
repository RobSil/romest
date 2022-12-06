package com.robsil.mainservice.util.dtoFactories

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.model.dto.BoardDto

class BoardDtosFactory {
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
