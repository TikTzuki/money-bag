package org.tiktuzki.moneybag.banking.tcb

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KLogging
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.devtools.NetworkInterceptor
import org.openqa.selenium.remote.http.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import org.tiktuzki.moneybag.banking.BankAuthenticator
import org.tiktuzki.moneybag.banking.TokenCredential
import org.tiktuzki.moneybag.user.UserRepository
import java.time.Duration


@Component
class TCBAuthenticator(val userRepository: UserRepository) : BankAuthenticator {

    @Throws()
    override fun login(userId: String, username: String, password: String): TokenCredential {
        val options = ChromeOptions()
        options.setCapability("goog:loggingPrefs", mapOf("performance" to "ALL"))
        val driver = ChromeDriver(options)

        var tokenCredential: TokenCredential? = null
        NetworkInterceptor(driver, Filter { next: HttpHandler ->
            HttpHandler { req: HttpRequest ->
                val res: HttpResponse = next.execute(req)
                if (req.method == HttpMethod.POST
                    && req.uri.contains("/auth/realms/backbase/protocol/openid-connect/token")
                ) {
                    tokenCredential = jacksonObjectMapper().readTree(res.content.get()).toToken()
                }
                res
            }
        })

        driver.get(TCB_URL)

        // enter login page
        driver.findElement(By.id("login-btn")).click()
        WebDriverWait(driver, Duration.ofSeconds(30))
            .until(ExpectedConditions.elementToBeClickable(By.id("kc-login")))

        // submit login
        driver.findElement(By.id("username")).sendKeys(username)
        driver.findElement(By.id("password")).sendKeys(password)
        driver.findElement(By.id("kc-login")).click()

        WebDriverWait(driver, Duration.ofMinutes(5))
            .until(ExpectedConditions.urlToBe(TCB_DASHBOARD_URL))
        logger.info { "Login successful to: " + driver.title + tokenCredential }
        driver.close()
        return tokenCredential ?: throw RuntimeException("Token not found")
    }

    override fun refreshToken(userId: String, tokenCredential: TokenCredential): TokenCredential {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("$TCB_IDENTITY_HOST/auth/realms/backbase/protocol/openid-connect/token")
            .addHeader("accept", "application/json, text/plain, */*")
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .post(
                FormBody.Builder()
                    .add("grant_type", "refresh_token")
                    .add("scope", "openid")
                    .add("refresh_token", tokenCredential.refreshToken)
                    .add("client_id", "tcb-web-client")
                    .add("ui_locales", "en-US")
                    .build()
            )
            .build()
        client.newCall(request).execute().use { resp ->
            if (resp.isSuccessful) {
                val newToken = jacksonObjectMapper().readTree(resp.body?.string()).toToken()
                logger.info { "Refreshing token success, new token ${newToken.accessToken}" }
                return newToken
            } else
                throw RuntimeException("Token refresh failed " + resp.body?.string())
        }
    }

    fun JsonNode.toToken() = TokenCredential(
        this.get("access_token").asText(),
        Duration.ofSeconds(this.get("expires_in").longValue()),
        this.get("refresh_token").asText(),
        Duration.ofSeconds(this.get("refresh_expires_in").longValue())
    )

    companion object : KLogging() {
        const val TCB_URL = "https://techcombank.com/khach-hang-ca-nhan"
        const val TCB_DASHBOARD_URL = "https://onlinebanking.techcombank.com.vn/dashboard"
        const val TCB_IDENTITY_HOST = "https://identity-tcb.techcombank.com.vn"
    }
}