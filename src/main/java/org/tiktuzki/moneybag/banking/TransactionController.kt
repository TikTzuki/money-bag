package org.tiktuzki.moneybag.banking

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks/transactions")
class TransactionController {

    @GetMapping
    fun getTransactions(): List<TransactionDto> {
        return emptyList()
    }
}