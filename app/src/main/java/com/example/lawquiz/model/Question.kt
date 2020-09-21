package com.example.lawquiz.model

import androidx.databinding.BaseObservable

data class Question(
    var id : String?,
    var questionBody : String,
    var choices : ArrayList<String>?,
    var correctAnswer : String,
    var ansRef : String,
    var classification : String,
    var difficulty : String,
    //if chosenAnswer = -1 then the question is not answered before
    var chosenAnswer: Int

){

    constructor():this("","",null,"","","","",-1)
}