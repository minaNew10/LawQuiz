package com.example.lawquiz.domain

data class Question(
    var id : String,
    var body : String,
    var answers : ArrayList<String>,
    var correctAnswer : Int,
    var ref : String,
    var category : String,
    var difficulty : String
)