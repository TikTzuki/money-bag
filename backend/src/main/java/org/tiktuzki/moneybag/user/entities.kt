package org.tiktuzki.moneybag.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class UserTbl(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID?,
    val username: String,
    val passwordHash: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val address: String,
    val createdAt: String,
    val updatedAt: String
)