package org.tiktuzki.moneybag.ai

import mu.KLogging
import org.springframework.ai.converter.StructuredOutputConverter
import org.springframework.util.StringUtils

@JvmRecord
data class DeepSeekModelResponse(val chainOfThought: String?, val answer: String?)

internal class DeepSeekModelOutputConverter : StructuredOutputConverter<DeepSeekModelResponse?> {
    override fun convert(text: String): DeepSeekModelResponse? {
        require(StringUtils.hasText(text)) { "Text cannot be blank" }
        val openingThinkTagIndex: Int = text.indexOf(OPENING_THINK_TAG)
        val closingThinkTagIndex: Int = text.indexOf(CLOSING_THINK_TAG)

        if (openingThinkTagIndex != -1 && closingThinkTagIndex != -1 && closingThinkTagIndex > openingThinkTagIndex) {
            val chainOfThought = text.substring(openingThinkTagIndex + OPENING_THINK_TAG.length, closingThinkTagIndex)
            val answer: String = text.substring(closingThinkTagIndex + CLOSING_THINK_TAG.length)
            return DeepSeekModelResponse(chainOfThought, answer)
        } else {
            logger.debug("No <think> tags found in the response. Treating entire text as answer.")
            return DeepSeekModelResponse(null, text)
        }
    }

    override fun getFormat(): String {
        return """
                <think>
                Chain of Thought
                </think>
                Answer
                
                """.trimIndent()
    }

    companion object : KLogging() {
        private const val OPENING_THINK_TAG = "<think>"
        private const val CLOSING_THINK_TAG = "</think>"
    }
}