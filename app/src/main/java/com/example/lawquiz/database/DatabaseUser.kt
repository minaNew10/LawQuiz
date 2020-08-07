package com.example.lawquiz.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lawquiz.domain.Question
import com.example.lawquiz.domain.Test
import com.example.lawquiz.domain.User
@Entity
data class DatabaseUser (
    @PrimaryKey
    val userId: String,
    val email: String,
    val bookMarkedQuestions: ArrayList<Question>? = null,
    val bookMarkedTests: ArrayList<Test>? = null,
    val bookMarkedRef: ArrayList<String>? = null,
    val score: Int = 0
)
fun DatabaseUser.asDomainUser() : User{
    return User(
        userId = this.userId,
        email = this.email
    )
}