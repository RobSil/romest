package com.robsil.service

import org.springframework.session.Session
import java.security.Principal

interface SessionService {

    fun getSessionById(sessionId: String): Session?

    fun deleteSessionById(sessionId: String): Unit

    fun deleteAllSessionsByName(name: String): Unit
}