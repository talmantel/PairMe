package com.idealtap.pairme.data.repository

import androidx.lifecycle.LiveData
import com.idealtap.pairme.common.Resource
import com.idealtap.pairme.data.model.Filters
import com.idealtap.pairme.data.model.Profile

interface ProfilesRepository {
    fun getNextSuggestedProfiles(filters: Filters): LiveData<Resource<List<Profile>>>
    fun updateProfiles(profiles: List<Profile>)
}