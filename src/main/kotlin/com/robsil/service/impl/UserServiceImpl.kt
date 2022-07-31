package com.robsil.service.impl

import com.robsil.data.domain.User
import com.robsil.data.repository.UserRepository
import com.robsil.model.exception.NotFoundException
import com.robsil.service.UserService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    private val log = logger()

    override fun getByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: run {
                log.info("userService getByEmail: user not found. Email: $email")
                throw NotFoundException("USER_NOT_FOUND")
            }
    }
}