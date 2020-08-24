package com.idealtap.pairme.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.idealtap.pairme.common.CompletionLiveData
import com.idealtap.pairme.common.NullableResource

interface UserRepository {
    fun getLoggedInUser(): LiveData<NullableResource<FirebaseUser?>>
    fun logInWithGoogle(idToken: String): CompletionLiveData<AuthResult>
    fun signOut()
}