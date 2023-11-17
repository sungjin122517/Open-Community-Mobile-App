package com.example.finalproject.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.finalproject.models.Event

class EventViewModel: ViewModel() {
    var event by mutableStateOf<Event?>(null)
            private set

    fun addEvent(newEvent: Event) {
        event = newEvent
    }
}