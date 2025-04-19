package org.tiktuzki.moneybag.banking.datamanager

import mu.KLogging
import org.springframework.stereotype.Component
import org.tiktuzki.moneybag.banking.Bank
import org.tiktuzki.moneybag.banking.TokenCredential
import org.tiktuzki.moneybag.banking.client.BankClientManager

@Component
class BankDatamanager(
    val bankClientManager: BankClientManager
) {
    fun sync(userId: String, bank: Bank, token: TokenCredential) {
        val client = bankClientManager.getClient(bank)
        val accounts = client.getBankAccounts(userId);
        logger.info { accounts }
//        val creditCards = client.getTermDeposits();
//        val termDeposits = client.getTermDeposits();
    }

    companion object : KLogging()
}