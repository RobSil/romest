package com.robsil.data.domain

import com.robsil.model.enum.StoringSource
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "photos")
class Photo(

    @Column
    @NotNull
    val path: String,

    @Column
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