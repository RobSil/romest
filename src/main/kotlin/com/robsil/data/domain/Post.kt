package com.robsil.data.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "posts")
class Post(

    @Column
    val title: String?,

    @Column
    val text: String,

    @OneToMany(mappedBy = "post")
    val photos: List<Photo>
) : BaseEntity() {

}