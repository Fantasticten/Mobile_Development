package com.example.fantasticten.view_model

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fantasticten.data.User

class UserViewModel : ViewModel() {
    private val _userData = MutableLiveData<User>()

    val userData: LiveData<User>
        get() = _userData

    fun setUser(user: User) {
        _userData.value = user
    }
}