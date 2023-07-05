package com.robsil.mainservice.data.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull


@Entity
@Table(name = "properties")
class Property (

    @Column(unique = true)
    @NotNull
    val name: String,

    @Column
    var value: String,

) : BaseEntity()
