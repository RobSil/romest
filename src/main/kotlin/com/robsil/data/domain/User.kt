package com.robsil.data.domain

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users")
class User(
    @Email
    @NotBlank
    @Column(name = "email", unique = true)
    var email: String,

    @Column(name = "password_hash")
    @NotBlank
    var passwordHash: String,

    @NotNull
    @Column(name = "is_blocked")
    var isBlocked: Boolean = false,


    ) : BaseEntity() {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "role_id", referencedColumnName = "id"))
    )
    var roles: MutableList<Role> = mutableListOf()

    fun addRole(role: Role): List<Role> {

        roles.add(role)

        return roles
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (email != other.email) return false
        if (passwordHash != other.passwordHash) return false
        if (isBlocked != other.isBlocked) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + passwordHash.hashCode()
        result = 31 * result + isBlocked.hashCode()
        return result
    }


}