package com.example.list_to_do.model


//@Entity
data class Task(
    val title: String,
    val hour: String,
    val date:String,
    val id: Int = 0
)
