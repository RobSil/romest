package com.robsil.mainservice.data.domain

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "roles")
class Role(
    @Column(unique = true)
    @NotNull
    val title: String
) : BaseEntity() {

//    constructor(id: Long) : this(null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Role

        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }

    override fun toString(): String {
        return "Role(title='$title')"
    }


}
