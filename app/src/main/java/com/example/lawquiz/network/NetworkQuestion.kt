package com.example.lawquiz.network

import androidx.room.Entity
import androidx.room.PrimaryKey


data class NetworkQuestion(
    var id : String,
    var body : String,
    var answers : ArrayList<String>,
    var correctAnswer : Int,
    var ref : String,
    var category : String,
    var difficulty : String,
    var favourited : Boolean = false
)