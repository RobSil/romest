package com.robsil

import com.robsil.data.domain.User
import com.robsil.data.repository.RoleRepository
import com.robsil.data.repository.UserRepository
import com.robsil.service.RoleService
import com.robsil.service.UserService
import com.robsil.service.impl.InitService
import org.apache.logging.log4j.kotlin.logger
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
@EnableJpaAuditing
@EnableWebSecurity
class MainApplication {
	@Bean
	fun commandLineRunner(
		initService: InitService
	): CommandLineRunner {
		return CommandLineRunner {
			initService.checkIsCreatedAdminUserIfNotCreateThen()
		}
	}
}

fun main(args: Array<String>) {

	runApplication<MainApplication>(*args)

}

