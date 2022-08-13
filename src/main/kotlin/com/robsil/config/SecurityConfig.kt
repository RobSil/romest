package com.robsil.config

import com.robsil.data.repository.UserRepository
import com.robsil.model.exception.NotFoundException
import org.apache.logging.log4j.kotlin.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig(
    val userRepository: UserRepository
): WebSecurityCustomizer {
    val logger = logger()

    override fun customize(web: WebSecurity?) {
        if (web == null) {
            return
        }

//        web.ignoring()
//            .antMatchers("/**")
//        web.
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity, sessionRegistry: SessionRegistry): SecurityFilterChain {

        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService<UserDetailsService>(object : UserDetailsService {
            override fun loadUserByUsername(username: String?): UserDetails {
                logger.info("loadUserByUsername: inside the method. Username: $username")
                username ?: throw NotFoundException()
                val user = userRepository.findByEmail(username) ?: throw NotFoundException()

                return User(user.email, user.passwordHash, user.roles.map { SimpleGrantedAuthority(it.title) })
            }
        })

//        http.authenticationProvider(DaoAuthenticationProvider())

        http
            .sessionManagement()
            .maximumSessions(5)
            .sessionRegistry(sessionRegistry)
            .expiredUrl("/login")

        http
            .formLogin()
            .loginProcessingUrl("/api/login")
            .successHandler { req, res, e -> res.status = 200 }
            .failureHandler { req, res, e ->
                res.setStatus(401)
            }

            .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler { req, res, e -> res.status = 200 }

        http
            .authorizeRequests()
            .anyRequest()
            .authenticated()


        return http.build()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}