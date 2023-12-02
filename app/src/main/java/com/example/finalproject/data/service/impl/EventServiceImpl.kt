package com.example.finalproject.data.service.impl

import android.content.ContentValues
import android.util.Log
import com.example.finalproject.data.model.Event
import com.example.finalproject.data.service.EventService
import com.example.finalproject.data.utils.await
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
//    private val auth: AuthService,
): EventService {
    val events = mutableMapOf<String, Event>()

    override fun addListener(
        date: Timestamp,
        onError: (Throwable) -> Unit,
        eventsSateSetter: (List<Event>) -> Unit
    ) {
        val query = firestore.collection(EVENT_COLLECTION)
                        .whereEqualTo(EVENT_IS_EXPIRED, false)

        query.addSnapshotListener { value, error ->
            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }
            value?.documentChanges?.forEach {
                Log.d(ContentValues.TAG, "Paco: event type =  ${it.type}")
                val wasEventAdded = (it.type == DocumentChange.Type.ADDED)
                val event = it.document.toObject(Event::class.java).copy()

                if (wasEventAdded) {
                    Log.d(ContentValues.TAG, "Paco: Add event: $event")
                    events[event.id] = event
                } else {
                    Log.d(ContentValues.TAG, "Paco: removed event: $event")
                    events.remove(event.id)
                    // remove ld events.
                }
            }
            eventsSateSetter(events.values.toList())

        }

    }

    override fun getEvents(date: Timestamp): MutableList<Event>? {
//        return firestore.collection(EVENT_COLLECTION)
//            .whereEqualTo(EVENT_IS_EXPIRED, false).get().await()?.toObjects(Event::class.java)
//
        return events.values.toMutableList()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val USER_COLLECTION = "users"
        private const val EVENT_COLLECTION = "events"
        private const val EVENT_IS_EXPIRED = "isExpired"
    }
}