package com.robsil.mainservice.data.domain

import com.robsil.mainservice.model.dto.BoardPickDto
import com.robsil.mainservice.model.dto.CompleteBoardDto
import com.robsil.mainservice.model.dto.SimpleBoardDto
import com.robsil.mainservice.util.minimize
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
//@Table(name = "boards", uniqueConstraints = )
@Table(name = "boards")
class Board(
    @Column
    @NotNull
    @NotBlank
    var name: String,

    @Column(name = "minimized_name")
    @NotNull
    @NotBlank
    var minimizedName: String,

    @Column(name = "is_private")
    @NotNull
    var isPrivate: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User,

) : BaseEntity() {

    constructor(id: Long, name: String, isPrivate: Boolean, user: User) : this(name, name.minimize(), isPrivate, user) {
        this.id = id
    }

    constructor(id: Long, name: String, minimizedName: String, isPrivate: Boolean, user: User) : this(name, minimizedName, isPrivate, user) {
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Board

        if (name != other.name) return false
        if (isPrivate != other.isPrivate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + isPrivate.hashCode()
        return result
    }

    fun toSimpleDto(): SimpleBoardDto {
        return SimpleBoardDto(this.id, this.name, this.minimizedName, this.isPrivate)
    }

    fun toCompleteDto(): CompleteBoardDto {
        return CompleteBoardDto(this.id, this.name, this.minimizedName, this.isPrivate, this.user.toSimpleDto())
    }

    fun toPickDto(): BoardPickDto {
        return BoardPickDto(this.id, this.name, this.isPrivate)
    }
}
