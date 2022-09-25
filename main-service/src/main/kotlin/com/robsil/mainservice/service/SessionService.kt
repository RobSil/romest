package com.robsil.mainservice.service

import org.springframework.session.Session

interface SessionService {

    fun getSessionById(sessionId: String): Session?

    fun deleteSessionById(sessionId: String): Unit

    fun deleteAllSessionsByName(name: String): Unit
}