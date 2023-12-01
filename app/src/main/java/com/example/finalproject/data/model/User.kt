package com.example.finalproject.data.model

data class User (
    val name: String = "User",
    val school: String = "HKUST",
    val uid: String = "0",
    val email: String = "",
    var savedPostIds: MutableList<String> = mutableListOf<String>()
)