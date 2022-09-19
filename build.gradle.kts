import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.1"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "com.robsil"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
	maven("https://jitpack.io")
}

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.session:spring-session-data-redis")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-configuration-processor")

	implementation("io.lettuce:lettuce-core")

	implementation("org.hibernate.validator:hibernate-validator:8.0.0.CR1")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")

	implementation("net.datafaker:datafaker:1.5.0")

	implementation("com.squareup.okhttp3:okhttp:4.10.0")
//	platform("com.squareup.okio:okio:3.2.0")
	implementation("com.github.imagekit-developer:imagekit-java:2.0.0")


	runtimeOnly("org.postgresql:postgresql")


	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.named("compileKotlin") {
	inputs.files(tasks.named("processResources"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}
