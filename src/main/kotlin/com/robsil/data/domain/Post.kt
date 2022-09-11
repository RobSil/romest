package com.robsil.data.domain

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import javax.persistence.*

@Entity
@Table(name = "posts")
class Post(

    @Column
    val title: String?,

    @Column
    val text: String?,

//    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator::class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    val board: Board

//    @OneToMany(mappedBy = "post")
//    val photos: List<Photo>
) : BaseEntity() {

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