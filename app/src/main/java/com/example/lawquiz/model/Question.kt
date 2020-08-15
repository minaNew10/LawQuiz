package com.example.lawquiz.model

data class Question(
    var id : String,
    var body : String,
    var answers : ArrayList<String>,
    var correctAnswer : Int,
    var ref : String,
    var category : String,
    var difficulty : String
)