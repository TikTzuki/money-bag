package org.tiktuzki.moneybag

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.chat.model.ChatModel
//import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@SpringBootApplication
class MoneyBagApplication

fun main(args: Array<String>) {
//    val client: SimpleCasualLM = SimpleCasualLM(GGML_MODEL_PATH)
//    client.infer("Once upon a time, there was a little girl named Lily.", System.out::print)
    runApplication<MoneyBagApplication>(*args)
}

fun <T> newDeserializer(mapperFun: (JsonNode) -> T) = object : JsonDeserializer<T>() {
    override fun deserialize(p: JsonParser, ctx: DeserializationContext): T = mapperFun(p.codec.readTree(p))
}

@Component
class MyCommandLineRunner(
    val chatClient: ChatClient,
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val res= chatClient.prompt()
            .user("hello")
            .call()
        println(res)
    }
}