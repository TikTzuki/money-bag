package org.tiktuzki.moneybag.banking.entities

import jakarta.persistence.*
import org.tiktuzki.moneybag.banking.Bank
import org.tiktuzki.moneybag.currency.GlobalCurrency
import org.tiktuzki.moneybag.currency.GlobalCurrencyConverter
import java.math.BigDecimal
import java.time.Instant
import java.time.Period

@Embeddable
data class Audit(
    var owner: String? = null,
    var createdBy: String? = null,
    var createdDate: Instant,
    var lastModifiedBy: String? = null,
    var lastModifiedDate: Instant
) {
    companion object {
        fun create(): Audit {
            val now = Instant.now()
            return Audit(
                createdDate = now,
                lastModifiedDate = now
            )
        }
    }
}

@Entity
data class FinanceAccount(
    // key fields
    @Id
    var id: String,
    var accountNumber: String,

    var name: String,
    var bank: Bank,
    var availableBalance: BigDecimal,

    @Convert(converter = GlobalCurrencyConverter::class)
    var currency: GlobalCurrency,

    @Embedded
    var audit: Audit,
)


@Entity
data class TermDeposit(
    // key fields
    @Id
    var id: String,
    var financeAccountId: String,

    var name: String,
    var number: String,
    var bookedBalance: BigDecimal,
    var interestRate: BigDecimal,
    var accruedInterest: BigDecimal,
    var termPeriod: Period,

    var openDate: Instant,
    var maturityDate: Instant,

    @Embedded
    var audit: Audit
)

@Entity
data class BankCreditDard(
    // key fields
    @Id
    var id: String,
    var financeAccountId: String,

    var name: String,
    var number: String,
    var creditLimit: BigDecimal,
    var outstandingPaymentsAmount: BigDecimal,

    var openDate: Instant,
    var closeDate: Instant,

    @Embedded
    var audit: Audit
)