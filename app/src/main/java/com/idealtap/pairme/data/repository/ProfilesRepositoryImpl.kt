package com.idealtap.pairme.data.repository

import com.idealtap.pairme.data.dao.ProfilesDao
import com.idealtap.pairme.data.model.Filters
import com.idealtap.pairme.data.model.Profile

class ProfilesRepositoryImpl(private val profilesDao: ProfilesDao): ProfilesRepository{

    companion object {
        @Volatile private var instance: ProfilesRepositoryImpl? = null

        fun getInstance(profilesDao: ProfilesDao) =
            instance ?: synchronized(this) {
                instance ?: ProfilesRepositoryImpl(profilesDao).also { instance = it }
            }
    }

    override fun getNextSuggestedProfiles(filters: Filters)
            = profilesDao.getSuggestedProfiles(filters, 2)

    override fun updateProfiles(profiles: List<Profile>) {
        profilesDao.updateProfiles(profiles)
    }

}