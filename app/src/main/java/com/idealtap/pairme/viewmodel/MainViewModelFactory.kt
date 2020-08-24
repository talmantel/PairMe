package com.idealtap.pairme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.idealtap.pairme.data.repository.ProfilesRepository
import com.idealtap.pairme.data.repository.UserRepository
import com.idealtap.pairme.ui.MainViewModel

class MainViewModelFactory(private val profilesRepo: ProfilesRepository, private val userRepo: UserRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(profilesRepo, userRepo) as T
    }
}