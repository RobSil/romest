package com.robsil.mainservice.data.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull


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
