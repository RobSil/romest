package com.robsil.mainservice.util.dtoFactories

import com.robsil.mainservice.data.domain.Board
import com.robsil.mainservice.model.dto.BoardDto

class BoardDtosFactory {
}


fun Board.toDto(): BoardDto {
    return BoardDto(this.id, this.name, this.isPrivate, listOf())
}
