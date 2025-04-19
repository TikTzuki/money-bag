package org.tiktuzki.moneybag.banking

import org.tiktuzki.moneybag.banking.entities.FinanceAccount

interface BakingCard

interface BankingClient {
    fun getBank(): Bank
    fun login(userId: String, username: String, password: String): TokenCredential
    fun getCards(userId: String): List<BakingCard>
    fun getBankAccounts(userId: String): List<FinanceAccount>
}

