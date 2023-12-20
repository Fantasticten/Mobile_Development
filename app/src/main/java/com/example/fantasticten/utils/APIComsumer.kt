package com.example.fantasticten.utils

import com.example.fantasticten.home_feature.chat.ChatMessage
import com.example.fantasticten.data.ArtikelData
import com.example.fantasticten.data.ArtikelResponse
import com.example.fantasticten.data.AuthResponse
import com.example.fantasticten.data.DoctorResponse
import com.example.fantasticten.data.LoginBody
import com.example.fantasticten.data.ProgramResponse
import com.example.fantasticten.data.QueueData
import com.example.fantasticten.data.RegisterBody
import com.example.fantasticten.data.ResponseKunjung
import com.example.fantasticten.data.ResponseNotif
import com.example.fantasticten.data.ResponseRiwayat
import com.example.fantasticten.data.UniqueEmailValidationResponse
import com.example.fantasticten.data.ValidateEmailBody

import com.example.fantasticten.data.editProfil



import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIComsumer {
    @POST("api/auth/validate-unique-email")
    suspend fun validateEmailAddress(@Body body: ValidateEmailBody): Response<UniqueEmailValidationResponse>

    @POST("api/auth/signup")
    suspend fun registerUser(@Body body: RegisterBody): Response<AuthResponse>

    @POST("api/auth/signin")
    suspend fun loginUser(@Body body: LoginBody): Response<AuthResponse>

    @GET("api/dokter")
    suspend fun getDoctors(): Response<DoctorResponse>

    @GET("api/program")
    suspend fun getPrograms(): Response<ProgramResponse>

    @GET("api/artic")
    suspend fun getartic(): Response<ArtikelResponse>

    @GET("api/artic/{id}")
    suspend fun getDetailArtikel(@Path("id") artikelId: String): Response<ArtikelData>


    @POST("/api/queue")
    fun addQueue(@Body queueData: QueueData): Call<Void>

    @GET("api/test/user")
    fun getUserContent(): Call<String?>?


    @GET("chat/riwayat/{id}")
    fun getChatHistory(@Path("id") userId: Int): Call<List<ChatMessage>>

    @GET("api/notifikasi/user/{userId}")
    fun getNotifikasi(@Path("userId") userId: Int): Call<ResponseNotif>

    @Headers("Content-Type: application/json")
    @POST("api/get-kunjungan-pasien/{id}")
    fun getPatientKunjungan(@Path("id") id: Int): Call<ResponseKunjung>

    @Headers("Content-Type: application/json")
    @POST("api/get-riwayat-pasien/{id}")
    fun getPatientHistory(@Path("id") id: Int): Call<ResponseRiwayat>



    @GET("api/treatment/user/{user_id}")
    suspend fun getTreatmentListByUser(@Path("user_id") userId: Int): Response<TratmentResponse>
    @GET("api/rekam-medis/{id}")
    suspend fun getRekamMedisById(@Path("id") id: Int): Response<DataRekamMedis>


    @FormUrlEncoded
    @PUT("/api/user/{id}")
    fun putUser(@Path("id")id: Int,
                @Field("username") username :String,
                @Field("tanggal_lahir") tanggal_lahir :String,
                @Field("jenis_kelamin") jenis_kelamin :String,
                @Field("phone_number") phone_number :String,
                @Field("email") email :String,
                @Field("alamat") alamat :String
                ):Call<editProfil>
    @GET("/api/user/{id}")
     fun getUser(@Path("id")id: Int):Call<editProfil>

}