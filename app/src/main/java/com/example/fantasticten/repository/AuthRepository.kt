package com.example.fantasticten.repository

import com.example.fantasticten.data.LoginBody
import com.example.fantasticten.data.RegisterBody
import com.example.fantasticten.data.UniqueEmailValidationResponse
import com.example.fantasticten.data.ValidateEmailBody
import com.example.fantasticten.utils.APIComsumer
import com.example.fantasticten.utils.RequestStatus
import com.example.fantasticten.utils.SimplifiedMessage
import kotlinx.coroutines.flow.flow

class AuthRepository(private val comsumer: APIComsumer) {
    fun validateEmailAddress(body: ValidateEmailBody) = flow  {
        emit(RequestStatus.Waiting)
        val response = comsumer.validateEmailAddress(body)
        if (response.isSuccessful) {
            emit((RequestStatus.Success(response.body()!!)))
        } else {
            emit(RequestStatus.Error(SimplifiedMessage.get(response.errorBody()!!.byteStream().reader().readText())))
        }
    }

    fun registerUser(body: RegisterBody) = flow {
        emit(RequestStatus.Waiting)
        val response = comsumer.registerUser(body)
        if (response.isSuccessful) {
            emit((RequestStatus.Success(response.body()!!)))
        } else {
            emit(RequestStatus.Error(SimplifiedMessage.get(response.errorBody()!!.byteStream().reader().readText())))
        }
    }

    fun loginUser(body: LoginBody) = flow {
        emit(RequestStatus.Waiting)
        val response = comsumer.loginUser(body)
        if (response.isSuccessful) {
            emit((RequestStatus.Success(response.body()!!)))
        } else {
            emit(RequestStatus.Error(SimplifiedMessage.get(response.errorBody()!!.byteStream().reader().readText())))
        }
    }
}