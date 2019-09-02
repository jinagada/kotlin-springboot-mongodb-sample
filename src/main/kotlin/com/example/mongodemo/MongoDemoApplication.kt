package com.example.mongodemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongoDemoApplication

fun main(args: Array<String>) {
    runApplication<MongoDemoApplication>(*args)
}
