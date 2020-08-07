package com.example.lawquiz.domain

data class Test(
    val id : String,
    val numOfQues : Int,
    val difficulty: String,
    val category: String,
    val questions : ArrayList<String>,
    val score : Int
)