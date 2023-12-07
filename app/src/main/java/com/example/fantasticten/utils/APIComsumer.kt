package com.example.fantasticten.utils

import com.example.fantasticten.data.RegisterBody
import com.example.fantasticten.data.AuthResponse
import com.example.fantasticten.data.LoginBody
import com.example.fantasticten.data.ResponseRiwayat
import com.example.fantasticten.data.UniqueEmailValidationResponse
import com.example.fantasticten.data.ValidateEmailBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface APIComsumer {
    @POST("api/auth/validate-unique-email")
    suspend fun validateEmailAddress(@Body body: ValidateEmailBody): Response<UniqueEmailValidationResponse>

    @POST("api/auth/signup")
    suspend fun registerUser(@Body body: RegisterBody): Response<AuthResponse>

    @POST("api/auth/signin")
    suspend fun loginUser(@Body body: LoginBody): Response<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("api/get-riwayat-pasien/{id}")
    fun getPatientHistory(@Path("id") id: Int): Call<ResponseRiwayat>

}