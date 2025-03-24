package org.tiktuzki.moneybag

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.tiktuzki.moneybag.user.UserDto
import org.tiktuzki.moneybag.user.UserRepository
import java.util.Currency

@SpringBootApplication
class MoneyBagApplication

fun main(args: Array<String>) {
    runApplication<MoneyBagApplication>(*args)
}

@Component
class MyCommandLineRunner(
    val userRepository: UserRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val userId = "me"
        userRepository.addUser(userId, UserDto(userId))
    }
}