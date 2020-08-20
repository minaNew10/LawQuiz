package com.example.lawquiz.model

data class Test(
    val id : String,
    val numOfQues : Int,
    val difficulty: String,
    val category: String?,
    val questions : ArrayList<Question?>?,
    val score : Int
)