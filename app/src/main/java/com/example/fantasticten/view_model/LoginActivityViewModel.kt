package com.example.fantasticten.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fantasticten.data.AuthResponse
import com.example.fantasticten.data.LoginBody
import com.example.fantasticten.data.User
import com.example.fantasticten.repository.AuthRepository
import com.example.fantasticten.utils.AuthToken
import com.example.fantasticten.utils.RequestStatus
import kotlinx.coroutines.launch

class LoginActivityViewModel(val authRepository: AuthRepository, val application: Application): ViewModel() {
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = false }
    private var errorMessage: MutableLiveData<HashMap<String, String>> = MutableLiveData()
    private var user: MutableLiveData<User> = MutableLiveData()

    private val authResponseLiveData: MutableLiveData<AuthResponse> = MutableLiveData()

    fun getAuthResponseLiveData(): LiveData<AuthResponse> = authResponseLiveData
    fun getIsLoading(): LiveData<Boolean> = isLoading
    fun getErrorMessage(): LiveData<HashMap<String, String>> = errorMessage
    fun getUser(): LiveData<User> = user

    private val userLiveData: MutableLiveData<User> = MutableLiveData()

    fun getUserLiveData(): LiveData<User> = userLiveData

    fun loginUser(body: LoginBody) {
        viewModelScope.launch {
            authRepository.loginUser(body).collect {
                when(it) {
                    is RequestStatus.Waiting -> {
                        isLoading.value = true
                    }
                    is RequestStatus.Success -> {
                        isLoading.value = false
                        user.value = it.data.user
                        AuthToken.getInstance(application.baseContext).token = it.data.token
                    }
                    is RequestStatus.Error -> {
                        isLoading.value = false
                        errorMessage.value = it.message
                    }
                }
            }
        }
    }
}