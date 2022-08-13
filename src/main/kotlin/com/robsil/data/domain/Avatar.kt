package com.robsil.data.domain

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "avatars")
class Avatar(

    @Column
    @NotNull
    val path: String,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: User

): BaseEntity() {
}