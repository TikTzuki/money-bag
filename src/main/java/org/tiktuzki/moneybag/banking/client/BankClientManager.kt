package org.tiktuzki.moneybag.banking.client

import mu.KLogging
import org.springframework.stereotype.Service
import org.tiktuzki.moneybag.banking.Bank
import org.tiktuzki.moneybag.banking.BankingClient

@Service
class BankClientManager(
    val client: Array<BankingClient>
) {
    val map: Map<Bank, BankingClient> = client.associateBy { it.getBank() }

    fun getClient(bankName: Bank): BankingClient {
        return map[bankName] ?: throw IllegalArgumentException("No client for bank $bankName")
    }

    companion object : KLogging()
}