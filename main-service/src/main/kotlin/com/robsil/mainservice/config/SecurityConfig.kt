package com.robsil.mainservice.config

import com.robsil.mainservice.data.repository.UserRepository
import com.robsil.mainservice.model.exception.NotFoundException
import org.apache.logging.log4j.kotlin.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig(
    val userRepository: UserRepository
) : WebSecurityCustomizer {
    val logger = logger()

    override fun customize(web: WebSecurity?) {
        if (web == null) {
            return
        }

//        web.ignoring()
//            .antMatchers(
//                "/api/v1/users/register",
//                "/api/login"
//            )

        web
            .debug(false)

    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun filterChain(
        http: HttpSecurity,
//                    sessionRegistry: SessionRegistry
    ): SecurityFilterChain {

//        val authenticationManagerBuilder = http.getSharedObject(
//            AuthenticationManagerBuilder::class.java
//        )
//        authenticationManagerBuilder.userDetailsService()

        val userDetailsService = UserDetailsService { username ->
            logger.info("loadUserByUsername: inside the method. Username: $username")
            username ?: throw NotFoundException()
            val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException("User cannot be found.")

            User(user.email, user.passwordHash, user.roles.map { SimpleGrantedAuthority(it.title) })
        }

        http.userDetailsService(userDetailsService)

        val provider = DaoAuthenticationProvider()

        provider.setUserDetailsService(userDetailsService)

        http.authenticationProvider(provider)

        http
            .csrf()
            .disable()

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .maximumSessions(5)
            .expiredUrl("/login")
//            .sessionAuthenticationStrategy(SessionAuthe)
//            .sessionRegistry(sessionRegistry)

        http
            .formLogin()
//            .loginPage("/api/login")
            .loginProcessingUrl("/login")
            .successHandler { req, res, e -> res.status = 200 }
            .failureHandler { req, res, e ->
                res.setStatus(401)
            }

            .and()
            .logout()
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .deleteCookies("SESSION", "JSESSIONID")
            .logoutSuccessHandler { req, res, e -> res.status = 200 }

        http
            .authorizeRequests()
            .antMatchers(
                "/api/v1/users/register",
                "/api/login",
                "/login"
//                "/api/v1/users/register",
//                "/api/login",
//                "/api/v1/init/ping"
            )
            .permitAll()

        http
            .authorizeRequests()
            .anyRequest()
            .authenticated()

//        http
//            .authorizeRequests()
//            .anyRequest()
//            .authenticated()


        return http.build()
    }

//    @Bean
//    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
//        return authenticationConfiguration.authenticationManager
//    }
}