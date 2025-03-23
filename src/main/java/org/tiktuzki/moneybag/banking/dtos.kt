package org.tiktuzki.moneybag.banking

import java.time.Duration
import java.time.Instant

open class TokenCredential(
    val accessToken: String,
    val expiresIn: Duration,
    val refreshToken: String,
    val refreshExpiresIn: Duration
)

open class TransactionDto(
    val id: String,
    val account: String,
    val cardId: String,
    val message: String,
    val timestamp: Instant,
)

open class CardDto(

)

// TCB
data class AccountDto(
    val id: String,
//    val additions: Additions,
//    val legalEntityIds: List<String>,
//    val name: String,
    val BBAN: String, // account number
//    val currency: String,
    val productKindName: String,
//    val productTypeName: String,
//    val bankBranchCode: String,
//    val creditAccount: Boolean = false,
//    val debitAccount: Boolean = false,
    val bookedBalance: Long, // available balance
    val availableBalance: Long, // available balance
    val creditLimit: Long?,
    val productId: String,
    val visible: Boolean,
    val favorite: Boolean,
    val product: Product,
    val displayName: String,

    // term deposit
    val accountOpeningDate: String?,
    val accountInterestRate: Float?,
    val termNumber: Int?,
)

//data class Additions(
//    val caWorkingBalance: String,
//    val maxAcctBalance: String,
//    val maxAccountType: String,
//    val caActualBalance: String,
//    val alias: String
//)

data class Product(
    val externalId: String,
    val externalTypeId: String,
    val typeName: String,
    val productKind: ProductKind
)

data class ProductKind(
    val id: Int,
    val externalKindId: String,
    val kindName: String,
    val kindUri: String
)