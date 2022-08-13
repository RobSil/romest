package com.robsil.config

import org.apache.logging.log4j.kotlin.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.session.SessionRegistry
import org.springframework.session.data.redis.RedisIndexedSessionRepository
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.session.security.SpringSessionBackedSessionRegistry

@Configuration
@EnableRedisHttpSession
//@EnableRedisWebSession
class HttpSessionConfig {

    private val log = logger()

    @Bean
    fun sessionRegistry(redisSessionRepository: RedisIndexedSessionRepository): SessionRegistry {
        return SpringSessionBackedSessionRegistry(redisSessionRepository)
    }

}