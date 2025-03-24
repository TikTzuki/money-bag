package org.tiktuzki.moneybag.banking.tcb

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component
import org.tiktuzki.moneybag.banking.*
import org.tiktuzki.moneybag.globalscheduler.GlobalScheduler
import org.tiktuzki.moneybag.user.UserRepository
import java.time.Duration

@Component
class TCBClientImpl(
    val globalScheduler: GlobalScheduler,
    val authenticator: TCBAuthenticator,
    val userRepository: UserRepository,
    val mapper: ObjectMapper
) : BankingClient {

    override fun login(userId: String, username: String, password: String) {
        logger.info("Logging in with $username and $password")
        val tokenCredential = authenticator.login(userId, username, password)
        userRepository.setBankSession(userId, Bank.TCB, tokenCredential)

        // schedule refresh token
        globalScheduler.addTask(
            "Refresh TCB $username Token",
            tokenCredential.refreshExpiresIn.minus(Duration.ofSeconds(60)),
            mutableMapOf("tokenCredential" to tokenCredential)
        ) { kwargs ->
            val oldToken = kwargs["tokenCredential"] as TokenCredential
            logger.info { "Refreshing token $oldToken" }
            try {
                val newCredential = authenticator.refreshToken(userId, oldToken)
                userRepository.setBankSession(userId, Bank.TCB, newCredential)
                kwargs["tokenCredential"] = newCredential
            } catch (e: Exception) {
                logger.error { e }
                userRepository.setBankSession(userId, Bank.TCB, null)
                globalScheduler.removeTask("Refresh TCB $username Token")
            }
        }
    }

    override fun getCards(userId: String): List<BakingCard> {
        getArrangement(userId)
        return emptyList()
    }

    fun getArrangement(userId: String) {
        val path = "/api/arrangement-manager/client-api/v2/productsummary/context/arrangements"
        val token = userRepository.getUserToken(userId, Bank.TCB).accessToken
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                )
            }.build()
        val url = HttpUrl.Builder()
            .scheme("https")
            .host(REST_API_HOST)
            .addPathSegment(path)
            .addQueryParameter("businessFunction", "Product Summary")
            .addQueryParameter("resourceName", "Product Summary")
            .addQueryParameter("privilege", "view")
//            .addQueryParameter("productKindName", "Current Account")
            .addQueryParameter("size", "1000000")
            .build()
        val request = Request.Builder()
            .url(url)
            .build()
        val accountsTypeRef = object : TypeReference<List<AccountDto>>() {}
        client.newCall(request).execute()
            .use { response ->
                if (!response.isSuccessful) {
                    throw RuntimeException("Unexpected code $response")
                }
                val accounts = mapper.readValue(response.body!!.bytes(), accountsTypeRef)
                logger.info { accounts }
            }
    }

    companion object : KLogging() {
        const val REST_API_HOST = "onlinebanking.techcombank.com.vn"
    }
}