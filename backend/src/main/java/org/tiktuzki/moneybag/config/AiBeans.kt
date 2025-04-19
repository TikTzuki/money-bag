package org.tiktuzki.moneybag.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.InMemoryChatMemory
import org.springframework.ai.chat.model.ChatModel
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AiBeans {
    @Bean
    fun chatMemory(): ChatMemory {
        return InMemoryChatMemory()
    }

    @Bean
    fun chatClient(chatModel: ChatModel, chatMemory: ChatMemory): ChatClient {
        return ChatClient
            .builder(chatModel)
            .defaultAdvisors(MessageChatMemoryAdvisor(chatMemory))
            .build()
    }
}