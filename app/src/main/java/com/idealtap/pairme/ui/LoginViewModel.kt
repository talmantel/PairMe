package com.idealtap.pairme.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.idealtap.pairme.common.CompletionLiveData
import com.idealtap.pairme.data.repository.UserRepository

class LoginViewModel(private val userRepo: UserRepository) : ViewModel() {
    val loggedInUser = userRepo.getLoggedInUser()

    fun loginWithGoogle(idToken: String): CompletionLiveData<AuthResult> {
        return userRepo.logInWithGoogle(idToken)
    }

    fun signOut(){
        userRepo.signOut()
    }

}