package org.tiktuzki.moneybag.banking.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tiktuzki.moneybag.banking.TransactionDto

@RestController
@RequestMapping("/api/banks/transactions")
class TransactionController {

    @GetMapping
    fun getTransactions(): List<TransactionDto> {
        return emptyList()
    }
}