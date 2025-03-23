package org.tiktuzki.moneybag.banking

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")
class BankController(val bankingClient: BankingClient) {
    @PostMapping("/login")
    fun login(userId: String, username: String, password: String) {
        bankingClient.login(userId, username, password)
    }
}