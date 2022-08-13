package com.robsil.controller

import com.robsil.model.UserRegisterDto
import com.robsil.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@RequestBody dto: UserRegisterDto) = userService.register(dto)

    @GetMapping("/information")
    fun getUserInfo(principal: Principal) {}
}