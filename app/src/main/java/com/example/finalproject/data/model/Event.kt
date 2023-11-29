package com.example.finalproject.data.model

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

data class Event(
//    val writer: User = User(),
    val title: String = "Tissue-engineering Integrated",
    val category: String = "Seminar",
    val tag: List<String> = listOf("environment", "education"),
    val description: String = "Since their first direct discovery in 2015, gravitational waves have contributed significantly to knowledge about astrophysics and fundamental physics. This talk will first introduce the Open... ",
    val language: List<String> = listOf("English", "Cantonese"),
    val location: String = "IAS4042, 4/F, Lo Ka Chung Building, Lee Shau Kee Campus, HKUST",
    val eventTime: Date = Date(),
    val uploadTime: Date = Date(),
    val formal: Boolean = false,
    val expired: Boolean = false,
    val registerLink: String = "www.google.com",
    val imageURL: String = "https://apru.org/wp-content/uploads/2021/12/HKUST.png"


)