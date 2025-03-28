package org.tiktuzki.moneybag

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.tiktuzki.moneybag.banking.datamanager.toFinanceAccount
import org.tiktuzki.moneybag.banking.entities.FinanceAccount
import org.tiktuzki.moneybag.user.UserDto
import org.tiktuzki.moneybag.user.UserRepository

@SpringBootApplication
class MoneyBagApplication

fun main(args: Array<String>) {
    runApplication<MoneyBagApplication>(*args)
}

fun <T> newDeserializer(mapperFun: (JsonNode) -> T) = object : JsonDeserializer<T>() {
    override fun deserialize(p: JsonParser, ctx: DeserializationContext): T = mapperFun(p.codec.readTree(p))
}

@Component
class MyCommandLineRunner(
    val mapper: ObjectMapper,
    val userRepository: UserRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        mapper.registerModule(kotlinModule())
        val module = SimpleModule()
        module.addDeserializer(FinanceAccount::class.java, newDeserializer(JsonNode::toFinanceAccount))
        mapper.registerModule(module)
        val userId = "me"
        userRepository.addUser(userId, UserDto(userId))
    }
}