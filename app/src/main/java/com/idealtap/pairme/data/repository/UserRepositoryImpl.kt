package com.idealtap.pairme.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.idealtap.pairme.common.CompletionLiveData
import com.idealtap.pairme.common.NullableResource

class UserRepositoryImpl(private var auth: FirebaseAuth): UserRepository{

    private val currentUser = MutableLiveData<NullableResource<FirebaseUser?>>()

    init{
        auth.addAuthStateListener {
            currentUser.postValue(NullableResource(auth.currentUser))
        }
    }

    companion object {
        @Volatile private var instance: UserRepositoryImpl? = null

        fun getInstance(auth: FirebaseAuth) =
            instance ?: synchronized(this) {
                instance ?: UserRepositoryImpl(auth).also { instance = it }
            }
    }

    override fun getLoggedInUser(): LiveData<NullableResource<FirebaseUser?>> {
        return currentUser
    }

    override fun logInWithGoogle(idToken: String): CompletionLiveData<AuthResult> {
        return signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
    }

    override fun signOut() {
        auth.signOut()
    }

    private fun signInWithCredential(credential: AuthCredential): CompletionLiveData<AuthResult>{
        val completionLiveData = CompletionLiveData<AuthResult>()
        auth.signInWithCredential(credential)
            .addOnCompleteListener(completionLiveData)

        return completionLiveData
    }

}