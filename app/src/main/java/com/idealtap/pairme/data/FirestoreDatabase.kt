package com.idealtap.pairme.data

import com.google.firebase.firestore.FirebaseFirestore
import com.idealtap.pairme.data.dao.ProfilesDao
import com.idealtap.pairme.data.dao.ProfilesDaoImpl

class FirestoreDatabase {
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    fun profilesDao(): ProfilesDao = ProfilesDaoImpl(firebaseFirestore)

    companion object {
        const val PROFILES_COLLECTION = "profiles"

        @Volatile private var instance: FirestoreDatabase? = null

        fun getInstance(): FirestoreDatabase {
            return instance
                ?: synchronized(this) {
                instance
                    ?: FirestoreDatabase()
                        .also { instance = it }
            }
        }
    }
}