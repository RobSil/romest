package com.robsil.mainservice.controller

import com.robsil.mainservice.service.SessionService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/sessions")
class SessionController(
    private val sessionService: SessionService
) {

    @DeleteMapping("/{sessionId}/delete")
    fun deleteSession(@PathVariable sessionId: String) = sessionService.deleteSessionById(sessionId)


}