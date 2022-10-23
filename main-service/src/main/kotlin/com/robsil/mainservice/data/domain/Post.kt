package com.robsil.mainservice.data.domain

import javax.persistence.*

@Entity
@Table(name = "posts")
class Post(

    @Column
    var title: String?,

    @Column
    var text: String?,

//    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator::class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    val board: Board,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", referencedColumnName = "id")
    val photo: Photo,

//    @OneToMany(mappedBy = "post")
//    val photos: List<Photo>
) : BaseEntity() {

    constructor(id: Long, title: String?, text: String?, board: Board, photo: Photo) : this(title, text, board, photo) {
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (title != other.title) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (text?.hashCode() ?: 0)
        return result
    }
}