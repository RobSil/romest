package com.robsil.mainservice.config

import org.apache.logging.log4j.kotlin.logger
import org.springframework.context.annotation.Configuration
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession


@Configuration
@EnableRedisHttpSession
//@EnableRedisWebSession
class HttpSessionConfig {



    private val log = logger()

//    fun sessionRepository(rcf: RedisConnectionFactory): RedisOperationsSessionRepository {
//        return RedisOperationsSessionRepository(rcf)
//    }

//    @Bean
//    fun sessionRegistry(sessionRepo: RedisIndexedSessionRepository): SessionRegistry {
//        return SpringSessionBackedSessionRegistry(sessionRepo)
//    }

}