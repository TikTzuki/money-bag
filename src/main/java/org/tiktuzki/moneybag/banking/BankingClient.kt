package org.tiktuzki.moneybag.banking

interface BakingCard

interface BankingClient {
    fun login(userId: String, username: String, password: String)
    fun getCards(userId: String): List<BakingCard>
}

