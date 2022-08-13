package com.robsil.controller

import com.robsil.service.impl.InitService
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/init")
class InitController(
    private val initService: InitService,
) {


    @GetMapping
    private fun init() {
        initService.fullInit()
    }

    @GetMapping("/redis")
    private fun testRedis() {
    }
}