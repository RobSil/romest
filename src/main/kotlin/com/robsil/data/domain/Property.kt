package com.robsil.data.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
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
