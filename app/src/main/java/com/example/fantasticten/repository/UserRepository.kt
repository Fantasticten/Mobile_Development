package com.example.fantasticten.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fantasticten.data.AuthResponse

class UserRepository {
    private val loggedInUser = MutableLiveData<AuthResponse>()

    fun setUserLoggedIn(authResponse: AuthResponse) {
        loggedInUser.value = authResponse
    }

    fun getLoggedInUser(): LiveData<AuthResponse> {
        return loggedInUser
    }
}
