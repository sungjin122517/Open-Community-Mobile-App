package com.example.finalproject.data.service

import com.example.finalproject.data.model.Event
import com.example.finalproject.data.model.Post
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow

interface EventService {
//    val event: MutableList<Event>?
    fun addListener(
        date: Timestamp,
        onError: (Throwable) -> Unit,
        onDocumentEvent: (List<Event>) -> Unit
    )

    fun getEvents(date: Timestamp): MutableList<Event>?
    fun getEvent(eventId: String): Flow<Event?>

}