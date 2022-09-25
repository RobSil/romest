package com.robsil.helloservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloServiceApplication {

}

fun main(args: Array<String>) {

    runApplication<HelloServiceApplication>(*args)

}