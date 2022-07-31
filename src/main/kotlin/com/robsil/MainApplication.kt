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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
@EnableWebSecurity
class MainApplication {
	@Bean
	fun commandLineRunner(
		userService: UserService,
		userRepository: UserRepository,
		initService: InitService,
		roleRepository: RoleRepository,
		roleService: RoleService,
		passwordEncoder: PasswordEncoder
	): CommandLineRunner {
		return CommandLineRunner {
			if (roleRepository.count() == 0L) {
				initService.initRoles()
			}

			if (userRepository.count() > 0) {
				return@CommandLineRunner
			}

			val email = "admin@robsil.com"
			val rawPassword = "1414"

			val saUser = User(email, passwordEncoder.encode(rawPassword), false)
			saUser.addRole(roleService.getByName("SUPERADMIN"))

			userRepository.save(saUser)

			println("Successfully created default super admin user. Email: $email, password: $rawPassword")
		}
	}
}

fun main(args: Array<String>) {


	runApplication<MainApplication>(*args)

}

