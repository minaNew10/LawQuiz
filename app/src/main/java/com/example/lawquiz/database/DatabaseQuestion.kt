package com.example.lawquiz.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lawquiz.domain.Question

@Entity
data class DatabaseQuestion(
    @PrimaryKey
    var id : String,
    var body : String,
    var answers : ArrayList<String>,
    var correctAnswer : Int,
    var ref : String,
    var category : String,
    var difficulty : String,
    var favourited : Boolean = false
)
fun DatabaseQuestion.asDomainQuestion(): Question{
    return Question(
        id = this.id,
        body = this.body,
        answers = this.answers,
        correctAnswer = this.correctAnswer,
        ref = this.ref,
        category = this.category,
        difficulty = this.difficulty
    )
}