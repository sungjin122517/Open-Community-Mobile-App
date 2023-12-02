package com.example.finalproject.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Event(
//    val writer: User = User(),
    @DocumentId val id: String = "",
    val title: String = "Tissue-engineering Integrated",
    val category: String = "Seminar",
    val tags: List<String> = listOf("environment", "education"),
    val description: String = "Since their first direct discovery in 2015, gravitational waves have contributed significantly to knowledge about astrophysics and fundamental physics. This talk will first introduce the Open... ",
    val language: String = "English",
    val location: String = "IAS4042, 4/F, Lo Ka Chung Building, Lee Shau Kee Campus, HKUST",
    val eventTime: Timestamp = Timestamp.now(),
    val uploadTime: Timestamp = Timestamp.now(),
    val isFormal: Boolean = false,
    var isExpired: Boolean = false,
    val registerLink: String = "www.google.com",
    val imageURL: String = "https://apru.org/wp-content/uploads/2021/12/HKUST.png"


)