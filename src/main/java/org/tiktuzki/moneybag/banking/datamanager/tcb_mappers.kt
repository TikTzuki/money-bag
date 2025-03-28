package org.tiktuzki.moneybag.banking.datamanager

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import org.tiktuzki.moneybag.banking.Bank
import org.tiktuzki.moneybag.banking.entities.Audit
import org.tiktuzki.moneybag.banking.entities.FinanceAccount
import org.tiktuzki.moneybag.banking.entities.TermDeposit
import org.tiktuzki.moneybag.currency.GlobalCurrency
import java.math.BigDecimal
import java.util.*

fun JsonNode.toFinanceAccount()= FinanceAccount(
        id = this.get("id").asText(),
        accountNumber = this.get("BBAN").asText(),
        name = this.get("accountHolderNames").asText(),
        bank = Bank.TCB,// node.get("bank").asText(),
        availableBalance = BigDecimal(this.get("availableBalance").asText()),
        currency = GlobalCurrency(this.get("currency").asText()),
        audit = Audit.create()
    )

fun JsonNode.toTermDeposit() = TermDeposit(
    id = this.get("id").asText(),
    accountNumber = this.get("BBAN").asText(),
    name = this.get("accountHolderNames").asText(),
    bank = Bank.TCB,
    availableBalance = BigDecimal(this.get("availableBalance").asText()),
    currency = GlobalCurrency(this.get("currency").asText()),
    accruedInterest = BigDecimal(this.get("accruedInterest").asText()),
    principalAmount = BigDecimal(this.get("principalAmount").asText()),
    accountInterestRate = this.get("accountInterestRate").asDouble(),
    termUnit = this.get("termUnit").asText(),
    termNumber = this.get("termNumber").asInt(),
    maturityDate = Date(this.get("maturityDate").asLong()),
    audit = Audit.create()
)

fun JsonNode.toBankCreditCard() = BankCreditCard(
    id = this.get("id").asText(),
    accountNumber = this.get("BBAN").asText(),
    name = this.get("accountHolderNames").asText(),
    bank = Bank.TCB,
    availableBalance = BigDecimal(this.get("availableBalance").asText()),
    currency = GlobalCurrency(this.get("currency").asText()),
    creditLimit = BigDecimal(this.get("creditLimit").asText()),
    outstandingPayment = BigDecimal(this.get("outstandingPayment").asText()),
    audit = Audit.create()
)