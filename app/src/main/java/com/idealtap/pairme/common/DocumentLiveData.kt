package com.idealtap.pairme.common

import androidx.lifecycle.LiveData

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

class DocumentLiveData<T>(private val ref: DocumentReference, private val type: Class<T>) :
            LiveData<Resource<T>?>(), EventListener<DocumentSnapshot> {
    private var registration: ListenerRegistration? = null

    override fun onEvent(snapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
        value =
            if(e != null) Resource(e)
            else Resource(snapshot!!.toObject(type)!!)
    }

    override fun onActive() {
        super.onActive()
        registration = ref.addSnapshotListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        registration?.remove()
        registration = null
    }
}