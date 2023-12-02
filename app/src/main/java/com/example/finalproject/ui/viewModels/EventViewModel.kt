package com.example.finalproject.ui.viewModels

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.model.Event
import com.example.finalproject.data.service.EventService
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class EventViewModel@Inject constructor(
    private val service: EventService
): ViewModel() {
    var event by mutableStateOf<Event?>(null)
            private set

    var eventList by mutableStateOf<List<Event>>(listOf<Event>())
        private set

    init {
        viewModelScope.launch() {
            service.addListener(Timestamp.now(), {}) { newEventList ->
                eventList = newEventList
            }
//            events::setValue

        }
    }


    fun addEvent(newEvent: Event) {
        event = newEvent
    }

    fun fetchEvents(date: Timestamp = Timestamp.now()): MutableList<Event>? {
//        Log.d(ContentValues.TAG, "Paco: today is $date")
//        viewModelScope.launch {
//            val events = service.getEvents(date)
//            Log.d(ContentValues.TAG, "Paco: events = $events")
//
//        }
//        return runBlocking{
//            service.getEvents(date)
//        }
        return service.getEvents(date)
    }
}