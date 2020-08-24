package com.idealtap.pairme.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.idealtap.pairme.common.Resource
import com.idealtap.pairme.data.model.Filters
import com.idealtap.pairme.data.model.Profile
import com.idealtap.pairme.data.repository.ProfilesRepository
import com.idealtap.pairme.data.repository.UserRepository

class MainViewModel(private val profilesRepo: ProfilesRepository, private val userRepo: UserRepository) : ViewModel() {

    private val filters = MutableLiveData<Filters>().apply { value = Filters(ageMin = 20, ageMax = 30)}

    val suggestedProfiles: LiveData<Resource<List<Profile>>> =
        Transformations.switchMap(filters, profilesRepo::getNextSuggestedProfiles)

    val loggedInUser = userRepo.getLoggedInUser()

    fun selectFromPair(selected: Profile, nonSelected: Profile){
        selected.liked = true
        nonSelected.disliked = 1 + (nonSelected.disliked ?: 0)
        profilesRepo.updateProfiles(listOf(selected, nonSelected))
    }
}