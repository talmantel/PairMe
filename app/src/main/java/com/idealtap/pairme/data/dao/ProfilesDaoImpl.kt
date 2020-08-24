package com.idealtap.pairme.data.dao

import com.google.firebase.firestore.FirebaseFirestore
import com.idealtap.pairme.common.QueryLiveData
import com.idealtap.pairme.data.FirestoreDatabase.Companion.PROFILES_COLLECTION
import com.idealtap.pairme.data.model.Filters
import com.idealtap.pairme.data.model.Profile
import java.util.*

class ProfilesDaoImpl(private val firestore: FirebaseFirestore): ProfilesDao {

    override fun getSuggestedProfiles(
        filters: Filters,
        maxAmount: Long
    ): QueryLiveData<Profile> {
        val collection = firestore.collection(PROFILES_COLLECTION)

        var query = collection.whereEqualTo(Profile.LIKED, false).limit(maxAmount)

        filters.ageMin?.let{
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -1*it)
            query = query.whereLessThanOrEqualTo(Profile.BIRTH_DATE, calendar.time)
        }

        filters.ageMax?.let{
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, -1*it)
            query = query.whereGreaterThanOrEqualTo(Profile.BIRTH_DATE, calendar.time)
        }

        return QueryLiveData(query, Profile::class.java)
    }

    override fun updateProfiles(profiles: List<Profile>){
        for(profile in profiles) {
            val profileReference = firestore.collection(PROFILES_COLLECTION).document(profile.id!!)
            profileReference.set(profile)
        }
    }
}