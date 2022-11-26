package com.robsil.mainservice.controller

import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.UserInformationDto
import com.robsil.mainservice.model.UserRegisterDto
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.UnauthorizedException
import com.robsil.mainservice.service.SessionService
import com.robsil.mainservice.service.UserService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val sessionService: SessionService
) {

    private val log = logger()

    @GetMapping
    fun getAll(): List<User> = userService.getAll()

    @GetMapping("/refresh")
    fun refresh(principal: Principal?): ResponseEntity<UserInformationDto> {
        return ResponseEntity(userService.getByPrincipal(principal).toInformationDto(), HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestBody dto: UserRegisterDto) : ResponseEntity<Unit> {
        userService.register(dto)

        return ResponseEntity<Unit>(HttpStatus.CREATED)
    }

    @DeleteMapping("/{userId}/invalidate")
    fun invalidateUserSession(@PathVariable userId: Long) =
        sessionService.deleteAllSessionsByName(userService.getById(userId).email)

    @DeleteMapping("/{email}/email/invalidate")
    fun invalidateUserSessionByEmail(@PathVariable email: String) {
    }

    @GetMapping("/information")
    fun getUserInfo(principal: Principal?): ResponseEntity<UserInformationDto> {
        val dto = principal?.let { userService.getByPrincipal(principal).toInformationDto() }
            ?: run { throw UnauthorizedException("Unauthorized") }

        return ResponseEntity(dto, HttpStatus.OK)
    }
//    fun getUserInfo(httpSession: HttpSession?): UserInformationDto {
//    fun getUserInfo(principal: Principal?, httpSession: HttpSession?): Session? {
//        return httpSession?.id?.let { return sessionService.getSessionById(it) }
//        return UserInformationDto(1L, "test", setOf())
//    }
//    fun getUserInfo(httpSession: HttpSession): UserInformationDto = UserInformationDto(1L, "test", setOf())
}