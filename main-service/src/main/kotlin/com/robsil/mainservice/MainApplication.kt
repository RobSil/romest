package com.robsil.mainservice

import com.robsil.mainservice.service.impl.InitService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

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

