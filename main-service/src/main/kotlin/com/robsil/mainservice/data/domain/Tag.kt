package com.robsil.mainservice.data.domain

import com.robsil.mainservice.model.dto.TagDto
import javax.persistence.*

@Entity
@Table(name = "tags")
class Tag(

    @Column(nullable = false, unique = true)
    val title: String,

) : BaseEntity() {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "posts_tags",
        joinColumns = arrayOf(JoinColumn(name = "tag_id", referencedColumnName = "id")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "post_id", referencedColumnName = "id"))
    )
    var posts: MutableSet<Post> = mutableSetOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tag

        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }

    fun toDto(): TagDto {
        if (this.id == null) {
            throw IllegalArgumentException("Tag is not persisted yet.")
        }
        return TagDto(this.id!!, this.title)
    }
}