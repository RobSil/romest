package com.robsil.mainservice.data.domain

import com.robsil.mainservice.model.enum.StoringSource
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "photos")
class Photo(

    // is it going to be intended as identifier for imageKit, and path for fileSystem?
    // definitely has to be added some unique relative id
    @Column
    @NotNull
    val path: String,

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    val storingSource: StoringSource,

//    @ManyToOne
//    @JoinColumn(name = "post_id", referencedColumnName = "id")
//    val post: Post

): BaseEntity() {

    constructor(id: Long, path: String, storingSource: StoringSource) : this(path, storingSource) {
        this.id = id
    }

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