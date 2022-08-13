package com.robsil.service.impl

import com.robsil.data.domain.User
import com.robsil.data.repository.RoleRepository
import com.robsil.data.repository.UserRepository
import com.robsil.model.enum.ERole
import com.robsil.service.FileService
import com.robsil.service.RoleService
import com.robsil.service.UserService
import com.robsil.util.createDirectoryIfExists
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@Service
class InitService(
    private val roleService: RoleService,
    private val fileService: FileService,
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,

    @Value("\${data.image.avatar.path}")
    private val avatarPath: String,

    @Value("\${data.image.photo.path}")
    private val photoPath: String
) {

    fun fullInit() {
        initRoles()
        checkIsCreatedAdminUserIfNotCreateThen()
        initDirectories()
    }

    fun checkIsCreatedAdminUserIfNotCreateThen() {
        if (roleRepository.count() == 0L) {
            initRoles()
        }

        if (userRepository.count() > 0) {
            return
        }

        val email = "admin@robsil.com"
        val rawPassword = "1414"

        val saUser = User(email, passwordEncoder.encode(rawPassword), false)
        saUser.addRole(roleService.getByName("SUPERADMIN"))

        userRepository.save(saUser)

        println("Successfully created default super admin user. Email: $email, password: $rawPassword")
    }
    fun initRoles() {
        roleService.create(ERole.SUPERADMIN.toString())
        roleService.create(ERole.ADMIN.toString())
        roleService.create(ERole.TESTER.toString())
        roleService.create(ERole.USER.toString())
    }

    fun initDirectories() {
        Files.createDirectories(Path.of(avatarPath))
        Files.createDirectories(Path.of(photoPath))
    }


}