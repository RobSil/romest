package com.robsil.service.impl

import com.robsil.service.SessionService
import org.springframework.session.Session
import org.springframework.session.data.redis.RedisIndexedSessionRepository
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class SessionServiceImpl(
    private val sessionRepository: RedisIndexedSessionRepository,
) : SessionService {
    override fun getSessionById(sessionId: String): Session? {
        return sessionRepository.findById(sessionId)
    }

    override fun deleteSessionById(sessionId: String) {
        sessionRepository.deleteById(sessionId)
    }

    override fun deleteAllSessionsByName(name: String) {
        val sessions = sessionRepository.findByPrincipalName(name)

        sessions.forEach { (sessionId, _) ->  deleteSessionById(sessionId)}
    }
}