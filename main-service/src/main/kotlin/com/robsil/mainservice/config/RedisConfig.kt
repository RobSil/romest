package com.robsil.mainservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer

@Configuration
class RedisConfig {

    private val hostName = "localhost"
    private val port = 6379

}

fun <K, V> getRedisTemplate(rcf: RedisConnectionFactory): RedisTemplate<K, V> {
    val redisTemplate = RedisTemplate<K, V>()

    redisTemplate.setConnectionFactory(rcf)
    redisTemplate.setDefaultSerializer(GenericJackson2JsonRedisSerializer())
    redisTemplate.afterPropertiesSet()

    return redisTemplate
}
