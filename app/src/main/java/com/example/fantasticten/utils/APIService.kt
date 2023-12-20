package com.example.fantasticten.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIService {
    private const val BASE_URL = "https://keydentalcare.isepwebtim.my.id/"

    fun getService(token: String?): APIComsumer {
        if (token.isNullOrBlank()) {
            Log.e("TOKEN_CHECK", "Token is null or blank")
        } else {
            Log.d("TOKEN_CHECK", "Token: $token")
        }

        val clientBuilder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        val interceptor = Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            if (!token.isNullOrBlank()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        clientBuilder.addInterceptor(interceptor)

        val client = clientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(APIComsumer::class.java)
    }
}
