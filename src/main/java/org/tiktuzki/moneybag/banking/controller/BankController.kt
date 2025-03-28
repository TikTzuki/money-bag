package org.tiktuzki.moneybag.banking.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tiktuzki.moneybag.banking.Bank
import org.tiktuzki.moneybag.banking.BankingClient
import org.tiktuzki.moneybag.banking.datamanager.BankDatamanager
import org.tiktuzki.moneybag.user.UserRepository

@RestController
@RequestMapping("/api/banks")
class BankController(
    val bankingClient: BankingClient,
    val bankDatamanager: BankDatamanager,
    val userRepository: UserRepository,
) {
    @PostMapping("/login")
    fun login(userId: String, bank: Bank, username: String, password: String) {
        var token = userRepository.getUserToken(userId, Bank.TCB)
        if (token == null) {
            token = bankingClient.login(userId, username, password)
        }
        bankDatamanager.sync(userId, Bank.TCB, token);
    }
}