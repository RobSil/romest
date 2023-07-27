package com.robsil.mainservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.robsil.mainservice.model.exception.NotFoundException
import com.robsil.mainservice.model.exception.UnauthorizedException
import com.robsil.mainservice.service.UserService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig(
    val userService: UserService,
    val objectMapper: ObjectMapper,

    @Value("\${spring.authentication.unauthorized-message}")
    private val unauthorizedMessage: String,
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
    fun filterChain(
        http: HttpSecurity,
//                    sessionRegistry: SessionRegistry
    ): SecurityFilterChain {

        http.cors()

//        val authenticationManagerBuilder = http.getSharedObject(
//            AuthenticationManagerBuilder::class.java
//        )
//        authenticationManagerBuilder.userDetailsService()

        val userDetailsService = UserDetailsService { username ->
            logger.info("loadUserByUsername: inside the method. Username: $username")
            username ?: throw NotFoundException()
            val user = try {
                userService.getByEmail(username)
            } catch (e: Exception) {
                throw UsernameNotFoundException("User cannot be found.")
            }

            User(user.email, user.passwordHash, user.roles.map { SimpleGrantedAuthority(it.title) })
//            val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException("User cannot be found.")
//
//            User(user.email, user.passwordHash, user.roles.map { SimpleGrantedAuthority(it.title) })
        }

        http.userDetailsService(userDetailsService)

        val provider = DaoAuthenticationProvider()

        provider.setUserDetailsService(userDetailsService)

        http.authenticationProvider(provider)

        http
            .exceptionHandling()
            .authenticationEntryPoint { req, res, e ->

                res.status = HttpStatus.UNAUTHORIZED.value()
                res.writer.write(unauthorizedMessage)
                res.writer.flush()

            }

        http
            .csrf()
            .disable()

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .maximumSessions(5)
//            .expiredUrl("/api/login")
//            .sessionAuthenticationStrategy(SessionAuthe)
//            .sessionRegistry(sessionRegistry)

        http
            .formLogin()
//            .loginPage("/api/login")
            .loginProcessingUrl("/api/login")
            .successHandler { req, res, authentication ->
                res.status = HttpStatus.OK.value()

                try {
                    val user = userService.getByEmail(userService.getNameFromPrincipals(authentication))

                    val dto = user.toInformationDto()

                    res.writer.write(objectMapper.writeValueAsString(dto))
                    res.writer.flush()
                } catch (e: Exception) {
                    throw UnauthorizedException("Occurred unexpectable situation..", e)
                }
            }
            .failureHandler { req, res, e ->
                res.status = HttpStatus.UNAUTHORIZED.value()
            }

            .and()
            .logout()
            .logoutUrl("/api/logout")
            .invalidateHttpSession(true)
            .deleteCookies("SESSION", "JSESSIONID")
            .logoutSuccessHandler { req, res, e -> res.status = 200 }

        http
            .authorizeRequests()
            .requestMatchers(
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
//            .anyRequest()
            .requestMatchers("/api/**")
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
