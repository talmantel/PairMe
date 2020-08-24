package com.idealtap.pairme.util

import com.google.firebase.auth.FirebaseAuth
import com.idealtap.pairme.data.FirestoreDatabase
import com.idealtap.pairme.data.repository.ProfilesRepository
import com.idealtap.pairme.data.repository.ProfilesRepositoryImpl
import com.idealtap.pairme.data.repository.UserRepository
import com.idealtap.pairme.data.repository.UserRepositoryImpl
import com.idealtap.pairme.viewmodel.LoginViewModelFactory
import com.idealtap.pairme.viewmodel.MainViewModelFactory

object InjectorUtils {

    private fun getProfilesRepository(): ProfilesRepository {
        return ProfilesRepositoryImpl.getInstance(FirestoreDatabase.getInstance().profilesDao())
    }

    private fun getUserRepository(): UserRepository {
        return UserRepositoryImpl.getInstance(FirebaseAuth.getInstance())
    }

    fun provideMainViewModelFactory(): MainViewModelFactory {
        val profileRepo = getProfilesRepository()
        val userRepo = getUserRepository()
        return MainViewModelFactory(profileRepo, userRepo)
    }

    fun provideLoginViewModelFactory(): LoginViewModelFactory {
        val userRepo = getUserRepository()
        return LoginViewModelFactory(userRepo)
    }
}