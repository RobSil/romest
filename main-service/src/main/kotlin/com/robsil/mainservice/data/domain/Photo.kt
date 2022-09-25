package com.robsil.mainservice.data.domain

import com.robsil.mainservice.model.enum.StoringSource
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "photos")
class Photo(

    @Column
    @NotNull
    val path: String,

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    val storingSource: StoringSource,

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    val post: Post

): BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Photo

        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        return path.hashCode()
    }
}