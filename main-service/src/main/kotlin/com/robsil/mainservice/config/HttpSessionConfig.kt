package com.robsil.mainservice.config

import org.apache.logging.log4j.kotlin.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.session.data.redis.RedisIndexedSessionRepository
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession


@Configuration
@EnableRedisHttpSession
//@EnableRedisWebSession
class HttpSessionConfig {

    private val log = logger()

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        return redisTemplate
    }

    @Bean
    fun redisIndexedSessionRepository(redisTemplate: RedisTemplate<String, Any>): RedisIndexedSessionRepository {
        return RedisIndexedSessionRepository(redisTemplate)
    }

}
