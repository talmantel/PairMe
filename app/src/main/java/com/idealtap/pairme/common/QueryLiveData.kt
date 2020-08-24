package com.idealtap.pairme.common

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.idealtap.pairme.data.model.Model


class QueryLiveData<T : Model>(private val query: Query, private val type: Class<T>) :
        LiveData<Resource<List<T>>>(),
        EventListener<QuerySnapshot?> {

    private var registration: ListenerRegistration? = null

    override fun onEvent(snapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {
        if (e != null) {
            value = Resource(e)
            return
        }

        value = Resource(documentToList(snapshots!!))
    }

    override fun onActive() {
        super.onActive()
        registration = query.addSnapshotListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        if (registration != null) {
            registration!!.remove()
            registration = null
        }
    }

    private fun documentToList(snapshots: QuerySnapshot): List<T> {
        val retList = ArrayList<T>()
        if (snapshots.isEmpty)
            return retList

        for (document in snapshots.documents) {
            retList.add(document.toObject(type)!!.withId(document.id))
        }

        return retList
    }
}