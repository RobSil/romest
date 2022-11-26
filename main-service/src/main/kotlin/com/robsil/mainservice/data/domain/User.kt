package com.robsil.mainservice.data.domain

import com.robsil.mainservice.model.UserInformationDto
import com.robsil.mainservice.model.enum.ERole
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users")
class User(

    @NotBlank
    @Column(name = "username", unique = true, nullable = false)
    var username: String,

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

    constructor(id: Long, username: String, email: String, passwordHash: String, isBlocked: Boolean) : this(username, email, passwordHash, isBlocked) {
        this.id = id
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "role_id", referencedColumnName = "id"))
    )
    var roles: MutableSet<Role> = mutableSetOf()

//    var likes: MutableSet<Like> = mutableSetOf()

    fun addRole(role: Role): Set<Role> {

        roles.add(role)

        return roles
    }

    fun removeRole(eRole: ERole): Set<Role> {

        roles.removeIf { role -> role.title.equals(eRole.title, true) }

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

    fun toInformationDto(): UserInformationDto {
        return UserInformationDto(this.id, this.username, this.email, this.roles.map { it.title }.toMutableSet())
    }
}