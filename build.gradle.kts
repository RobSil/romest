import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val mockkVersion: String by properties
val jacksonDatatypeJsr310Version: String by properties
val imageKitVersion: String by properties
val log4jApiKotlinVersion: String by properties
val datafakerVersion: String by properties
val okHttpVersion: String by properties

plugins {
	id("org.springframework.boot") version "3.1.1"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
}

group = "com.robsil"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}



allprojects {
	repositories {
		mavenCentral()
		maven("https://jitpack.io")
	}

	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "kotlin-spring")

	dependencies {

		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-actuator")
		runtimeOnly("org.springframework.boot:spring-boot-devtools")
		implementation("org.springframework.boot:spring-boot-configuration-processor")


		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

		implementation("org.jetbrains.kotlin:kotlin-reflect")

		implementation("org.apache.logging.log4j:log4j-api-kotlin:$log4jApiKotlinVersion")

		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-data-redis")
		implementation("org.springframework.boot:spring-boot-starter-security")
		implementation("org.springframework.boot:spring-boot-starter-graphql")
		implementation("org.springframework.boot:spring-boot-configuration-processor")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.springframework.session:spring-session-data-redis")

//		implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonDatatypeJsr310Version")

		implementation("io.lettuce:lettuce-core")

//		implementation("org.hibernate.validator:hibernate-validator:8.0.0.CR1")

		implementation("net.datafaker:datafaker:$datafakerVersion")

		implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
//	platform("com.squareup.okio:okio:3.2.0")
		implementation("com.github.imagekit-developer:$imageKitVersion")

		implementation("org.springdoc:springdoc-openapi-ui:1.6.12")
		runtimeOnly("org.springdoc:springdoc-openapi-kotlin:1.6.12")


		runtimeOnly("org.postgresql:postgresql")




		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.mockk:mockk:$mockkVersion")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
