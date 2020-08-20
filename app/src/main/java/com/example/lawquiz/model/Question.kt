package com.example.lawquiz.model

data class Question(
    // TODO: 8/19/2020 define a no argument constructor 
    var id : String?,
    var questionBody : String,
    var choices : ArrayList<String>?,
    var correctAnswer : String,
    var ansRef : String,
    var classification : String,
    var difficulty : String
){
    constructor():this("","",null,"","","","")
}