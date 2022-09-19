package com.robsil.service.impl

import com.robsil.data.domain.Role
import com.robsil.data.domain.User
import com.robsil.data.repository.UserRepository
import com.robsil.model.UserRegisterDto
import com.robsil.model.enum.ERole
import com.robsil.model.exception.ForbiddenException
import com.robsil.model.exception.NotFoundException
import com.robsil.service.RoleService
import com.robsil.service.UserService
import org.apache.logging.log4j.kotlin.logger
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
        if (principal == null) throw ForbiddenException("INVALID_SESSION")
        return getByEmail(principal.name)
    }

    fun saveEntity(user: User): User {
        return userRepository.save(user)
    }

    override fun register(dto: UserRegisterDto): User {
        var user = User(dto.email, passwordEncoder.encode(dto.password), false)
        user.addRole(roleService.getByName(ERole.USER))

        user = saveEntity(user)

        return user
    }

    override fun getUserInformation(user: User): User {
        TODO("Not yet implemented")
    }
}