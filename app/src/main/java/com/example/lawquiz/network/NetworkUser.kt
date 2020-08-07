package com.example.lawquiz.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lawquiz.database.DatabaseUser
import com.example.lawquiz.domain.Question
import com.example.lawquiz.domain.Test
import com.example.lawquiz.domain.User

data class NetworkUser (

    val userId: String,
    val email: String
)
fun NetworkUser.asDomainModel() : User{
    return User(
        userId = this.userId,
        email = this.email
    )
}
fun NetworkUser.asDatabaseUser(): DatabaseUser{
    return DatabaseUser(
        userId = this.userId,
        email = this.email
    )
}