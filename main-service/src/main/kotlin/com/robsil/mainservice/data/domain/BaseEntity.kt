package com.robsil.mainservice.data.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

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
