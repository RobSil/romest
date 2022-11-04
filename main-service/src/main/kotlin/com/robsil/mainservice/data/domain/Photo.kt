package com.robsil.mainservice.data.domain

import com.robsil.mainservice.model.enum.StoringSource
import java.lang.IllegalArgumentException
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "photos")
class Photo(

    // is it going to be intended as identifier for imageKit, and path for fileSystem?
    // definitely has to be added some unique relative id
    // upd: now it's actually path
    @Column
    @NotNull
    val path: String,

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    val storingSource: StoringSource,

    @Column(name = "image_kit_id")
    @NotNull
    val imageKitId: String = "",
    @Column(name = "image_kit_url")
    @NotNull
    val imageKitUrl: String = "",

//    @ManyToOne
//    @JoinColumn(name = "post_id", referencedColumnName = "id")
//    val post: Post

): BaseEntity() {

    // going to be not null if there storing source is imagekit


    constructor(id: Long, path: String, storingSource: StoringSource) : this(path, storingSource) {
        this.id = id
        if (storingSource == StoringSource.IMAGEKIT)
            throw IllegalArgumentException("Using invalid constructor for imageKit. Image kit id or URL cannot be null.")
    }

    constructor(id: Long, path: String, storingSource: StoringSource, imageKitId: String, imageKitUrl: String) : this(path, storingSource, imageKitId, imageKitUrl) {
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