package org.tiktuzki.moneybag

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.tiktuzki.moneybag.banking.tcb.TCBClientImpl
import org.tiktuzki.moneybag.user.User
import org.tiktuzki.moneybag.user.UserRepository

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
        userRepository.addUser(userId, User(userId))
    }
}