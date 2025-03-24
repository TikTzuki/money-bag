package org.tiktuzki.moneybag.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.tiktuzki.moneybag.banking.Bank
import org.tiktuzki.moneybag.banking.TokenCredential

data class BankSession(
    val isLogged: Boolean,
    val token: TokenCredential
)

data class UserDto(
    val username: String,
    val bankSessions: MutableMap<Bank, BankSession> = mutableMapOf()
)

@Repository
interface UserRepos : JpaRepository<UserTbl, Long>

@Component
class UserRepository(
    val userRepos: UserRepos
) {
    val users: MutableMap<String, UserDto> = mutableMapOf()
    fun addUser(userId: String, user: UserDto) {
        users[userId] = user
        userRepos.save(UserTbl(null, userId, "", "", "", "", "", "", "", "", ""))
    }

    fun getUserToken(userId: String, bank: Bank): TokenCredential {
        return users[userId]?.bankSessions?.get(bank)?.token ?: throw RuntimeException("User not logged in")
    }

    fun setBankSession(userId: String, bank: Bank, token: TokenCredential?) {
        if (token == null) {
            users[userId]?.bankSessions?.remove(bank)
            return
        }
        users[userId]?.bankSessions?.put(bank, BankSession(true, token))
    }
}
