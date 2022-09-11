package com.robsil.data.domain

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
open class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long? = null

    @Column(name = "created_date")
    @CreationTimestamp
    var createdDate: LocalDateTime? = null

    @Column(name = "last_modified_date")
    var lastModifiedDate: LocalDateTime? = null

    constructor() {}

    constructor(id: Long?) {
        this.id = id
    }
    @PrePersist
    private fun prePersist() {
        this.createdDate = LocalDateTime.now()
        this.lastModifiedDate = LocalDateTime.now()
    }

    @PreUpdate
    private fun preUpdate() {
        this.lastModifiedDate = LocalDateTime.now()
    }
}
