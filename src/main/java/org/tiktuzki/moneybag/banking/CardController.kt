package org.tiktuzki.moneybag.banking

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks/cards")
class CardController(val bankingClient: BankingClient) {

    @GetMapping
    fun getCards(userId: String): List<CardDto> {
        bankingClient.getCards(userId)
        return emptyList()
    }
}