package com.robsil.mainservice.controller

import com.robsil.mainservice.data.domain.User
import com.robsil.mainservice.model.UserInformationDto
import com.robsil.mainservice.model.UserRegisterDto
import com.robsil.mainservice.model.dto.ComplexPostPageableDto
import com.robsil.mainservice.model.exception.ForbiddenException
import com.robsil.mainservice.model.exception.UnauthorizedException
import com.robsil.mainservice.service.PostService
import com.robsil.mainservice.service.SessionService
import com.robsil.mainservice.service.UserService
import com.robsil.mainservice.service.facade.PostServiceFacade
import org.apache.logging.log4j.kotlin.logger
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val sessionService: SessionService,
    private val postServiceFacade: PostServiceFacade,
    private val postService: PostService,
) {

    private val log = logger()

    @GetMapping
    fun getAll(): List<User> = userService.getAll()

    @GetMapping("/refresh")
    fun refresh(principal: Principal?): ResponseEntity<UserInformationDto> {
        return ResponseEntity(userService.getByPrincipal(principal).toInformationDto(), HttpStatus.OK)
    }

    @GetMapping("/{username}/posts")
    fun getAllPostsByUsername(@PathVariable username: String,
                              principal: Principal?,
                              @RequestParam(required = false, defaultValue = "0") pageNumber: Int,
                              @RequestParam(required = false, defaultValue = "20") pageSize: Int,): ResponseEntity<ComplexPostPageableDto> {
        val user = userService.getByPrincipal(principal)

        if (user.username != username) {
            throw ForbiddenException("You are not allowed to access this.")
        }

        val posts = postService.getAllByUsername(
            username,
            PageRequest.of(pageNumber, pageSize),
        ).map { postServiceFacade.toComplexPostDto(it) }
        return ResponseEntity(
            ComplexPostPageableDto(
                posts.toList(),
                posts.totalElements,
                posts.totalPages
            ), HttpStatus.OK
        )
    }

    @GetMapping("/{username}/likedPosts")
    fun getAllLikedPostsByUsername(@PathVariable username: String,
                                   principal: Principal?,
                                   @RequestParam(required = false, defaultValue = "0") pageNumber: Int,
                                   @RequestParam(required = false, defaultValue = "20") pageSize: Int,): ResponseEntity<ComplexPostPageableDto> {
        val user = userService.getByPrincipal(principal)

        if (user.username != username) {
            throw ForbiddenException("You are not allowed to access this.")
        }

        val posts = postService.getAllLikedByUsername(
            username,
            PageRequest.of(pageNumber, pageSize),
        ).map { postServiceFacade.toComplexPostDto(it) }
        return ResponseEntity(
            ComplexPostPageableDto(
                posts.toList(),
                posts.totalElements,
                posts.totalPages
            ), HttpStatus.OK
        )
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
