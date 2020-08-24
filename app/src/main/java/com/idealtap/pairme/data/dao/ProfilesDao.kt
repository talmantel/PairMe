package com.idealtap.pairme.data.dao

import com.idealtap.pairme.common.QueryLiveData
import com.idealtap.pairme.data.model.Filters
import com.idealtap.pairme.data.model.Profile


interface ProfilesDao {
    fun getSuggestedProfiles(filters: Filters, maxAmount: Long): QueryLiveData<Profile>
    fun updateProfiles(profiles: List<Profile>)
}