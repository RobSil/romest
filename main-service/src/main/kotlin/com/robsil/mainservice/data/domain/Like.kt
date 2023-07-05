package com.robsil.mainservice.data.domain

import com.robsil.mainservice.model.dto.LikeDto
import com.robsil.mainservice.util.dtoFactories.toSimpleDto
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

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
