package org.tiktuzki.moneybag

//import org.springframework.ai.ollama.OllamaChatModel
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.ai.chat.client.ChatClient
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.tiktuzki.moneybag.ai.DeepSeekModelOutputConverter
import java.util.*


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
        val chatId = UUID.randomUUID().toString()
        val res = chatClient.prompt()
            .user("hello")
            .advisors { advisorSpec ->
                advisorSpec
                    .param("chat_memory_conversation_id", chatId)
            }
            .call()
            .entity(DeepSeekModelOutputConverter ())
        println(res)
    }
}