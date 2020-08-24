package com.idealtap.pairme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.idealtap.pairme.data.repository.UserRepository
import com.idealtap.pairme.ui.LoginViewModel

class LoginViewModelFactory(private val userRepo: UserRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(userRepo) as T
    }
}