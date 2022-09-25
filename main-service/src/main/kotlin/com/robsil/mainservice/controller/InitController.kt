package com.robsil.mainservice.controller

import com.robsil.mainservice.service.impl.InitService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/init")
class InitController(
    private val initService: InitService,
) {

    @GetMapping("/ping")
    private fun pingPong(): String = "PONG"

    @GetMapping("/securePing")
    private fun securePingPong(): String = "SECURE_PONG"

    @GetMapping
    private fun init() {
        initService.fullInit()
    }

    @GetMapping("/redis")
    private fun testRedis() {
    }

    @GetMapping("/generatePosts")
    private fun generatePosts(@RequestParam count: Int, boardId: Long) = initService.generatePosts(count, boardId)
}