package com.robsil.mainservice.data.domain

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "properties")
class Property (

    @Column(unique = true)
    @NotNull
    val name: String,

    @Column
    var value: String,

) : BaseEntity()
