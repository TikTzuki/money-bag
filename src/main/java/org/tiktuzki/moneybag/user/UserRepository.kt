package org.tiktuzki.moneybag.user

import org.springframework.stereotype.Component
import org.tiktuzki.moneybag.banking.Bank
import org.tiktuzki.moneybag.banking.TokenCredential

data class BankSession(
    val isLogged: Boolean,
    val token: TokenCredential
)

data class User(
    val username: String,
    val bankSessions: MutableMap<Bank, BankSession> = mutableMapOf()
)

@Component
class UserRepository {
    val users: MutableMap<String, User> = mutableMapOf()
    fun addUser(userId: String, user: User) {
        users[userId] = user
    }

    fun getUserToken(userId: String, bank: Bank): TokenCredential {
        return users[userId]?.bankSessions?.get(bank)?.token ?: throw RuntimeException("User not logged in")
    }

    fun setBankSession(userId: String, bank: Bank, token: TokenCredential?) {
        if(token == null) {
            users[userId]?.bankSessions?.remove(bank)
            return
        }
        users[userId]?.bankSessions?.put(bank, BankSession(true, token))
    }
}