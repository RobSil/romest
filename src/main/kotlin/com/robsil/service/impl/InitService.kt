package com.robsil.service.impl

import com.robsil.service.RoleService
import org.springframework.stereotype.Service

@Service
class InitService(
    private val roleService: RoleService
) {

    fun initRoles() {
        roleService.create("SUPERADMIN")
        roleService.create("ADMIN")
        roleService.create("TESTER")
        roleService.create("USER")
    }
}