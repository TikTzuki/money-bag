package org.tiktuzki.moneybag.banking

interface BankAuthenticator {
    fun login(userId:String, username: String, password: String): TokenCredential
    fun refreshToken(userId:String, tokenCredential: TokenCredential): TokenCredential
}