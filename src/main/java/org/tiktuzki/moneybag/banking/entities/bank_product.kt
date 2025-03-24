package org.tiktuzki.moneybag.banking.entities

import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.tiktuzki.moneybag.banking.Bank
import org.tiktuzki.moneybag.currency.GlobalCurrency
import org.tiktuzki.moneybag.currency.GlobalCurrencyConverter
import java.util.*

@Entity
data class BankAccount(
    @Id
    var id: String,
    var accountNumber: String,
    var name: String,
    var bank: Bank,
    @Convert(converter = GlobalCurrencyConverter::class)
    var currency: GlobalCurrency,
    var current: Currency,
    val userId: UUID
)
