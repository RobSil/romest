package com.robsil.mainservice.service.impl

import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.data.repository.UserRepository
import com.robsil.mainservice.model.UserRegisterDto
import com.robsil.mainservice.model.enum.ERole
import com.robsil.mainservice.model.exception.DataUniqueViolationException
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.model.exception.UnauthorizedException
import com.robsil.mainservice.model.exception.constant.PostExceptionMessages.PRINCIPAL_NULL
import com.robsil.mainservice.service.RoleService
import com.robsil.mainservice.service.UserService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.boot.actuate.endpoint.SecurityContext
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Principal

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleService: RoleService,
) : UserService {

    private val log = logger()

    override fun getAll(): List<User> {
        return userRepository.findAll()
    }

    override fun getById(id: Long): User {
        return userRepository.findById(id).orElse(null)
            ?: run {
                log.info("userService getById: user not found. ID: $id")
                throw NotFoundException("USER_NOT_FOUND")
            }
    }

    override fun getByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: run {
                log.info("userService getByEmail: user not found. Email: $email")
                throw NotFoundException("USER_NOT_FOUND")
            }
    }

    override fun getByPrincipal(principal: Principal?): User {
        if (principal == null) throw UnauthorizedException(PRINCIPAL_NULL)
        return getByEmail(principal.name)
    }

    override fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
        val email: String = authentication.name ?: getNameFromPrincipals(authentication)

        return getByEmail(email)
    }

    override fun getNameFromPrincipals(authentication: Authentication): String {
        val principal = authentication.principal
        if (principal is UserDetails) {
            return principal.username
        }
        if (principal is Principal) {
            return principal.name
        }

        throw UnauthorizedException("User isn't authenticated.")
    }

    fun saveEntity(user: User): User {
        return userRepository.save(user)
    }

    override fun register(dto: UserRegisterDto): User {
        var user = User(dto.username, dto.email, passwordEncoder.encode(dto.password), false)
//        var user = User(dto.email, "", false)
        user.addRole(roleService.getByName(ERole.USER))

        user = try {
            saveEntity(user)
        } catch (e: DataIntegrityViolationException) {
            throw DataUniqueViolationException("During saving an user, data violation exception occurred", e)
        }

        return user
    }

    override fun getUserInformation(user: User): User {
        TODO("Not yet implemented")
    }
}