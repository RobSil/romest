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

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//        name = "posts_boards",
//        joinColumns = arrayOf(JoinColumn(name = "board_id", referencedColumnName = "id")),
//        inverseJoinColumns = arrayOf(JoinColumn(name = "post_id", referencedColumnName = "id"))
//    )
//    val posts: MutableSet<Post> = mutableSetOf()

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "board_id", referencedColumnName = "id")
//    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator::class)
//    val posts: Set<Post> = mutableSetOf()

//    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
//    @JsonSerialize(using = SimpleListPostSerializer::class)
//    val posts: List<Post> = mutableListOf()

//    fun addPost(post: Post): Set<Post> {
//        posts.add(post)
//
//        return posts
//    }
//
//    fun removePost(post: Post): Set<Post> {
//        posts.remove(post)
//
//        return posts
//    }

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
//    return if (this.id != null) SimpleBoardDto(this.id, this.name, this.isPrivate)
//    else SimpleBoardDto(-1L, this.name, this.isPrivate)
    }

    fun toCompleteDto(): CompleteBoardDto {
        return CompleteBoardDto(this.id, this.name, this.minimizedName, this.isPrivate, this.user.toSimpleDto())
    }

    fun toPickDto(): BoardPickDto {
        return BoardPickDto(this.id, this.name, this.isPrivate)
    }
}
