package com.robsil.mainservice.data.domain

import com.robsil.mainservice.model.dto.LikeDto
import com.robsil.mainservice.util.dtoFactories.toSimpleDto
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "likes")
class Like(

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post

) : BaseEntity() {


    fun toDto(): LikeDto {
        return LikeDto(user.toInformationDto(), post.toSimpleDto())
    }
}