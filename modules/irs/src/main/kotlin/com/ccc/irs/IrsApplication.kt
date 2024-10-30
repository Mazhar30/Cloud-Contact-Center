package com.ccc.irs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class IrsApplication

fun main(args: Array<String>) {
    runApplication<IrsApplication>(*args)
}
