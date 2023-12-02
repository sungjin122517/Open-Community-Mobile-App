package com.example.finalproject.data.service

import com.example.finalproject.data.model.Event
import com.google.firebase.Timestamp

interface EventService {
//    val event: MutableList<Event>?
    fun addListener(
        date: Timestamp,
        onError: (Throwable) -> Unit,
        onDocumentEvent: (List<Event>) -> Unit
    )

    fun getEvents(date: Timestamp): MutableList<Event>?

}