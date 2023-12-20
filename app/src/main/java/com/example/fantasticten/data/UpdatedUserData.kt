package com.example.fantasticten.data

data class Users(
    val id: Int,
    val username: String,
    val email: String,
    val phone_number: String,
    val profile_photo: String,
    val tanggal_lahir: String?,
    val jenis_kelamin: String?,
    val alamat: String?,
    val createdAt: String,
    val updatedAt: String
)

data class UpdatedUserData(
    val username: String?,
    val email: String?,
    val phone_number: String?,
    val tanggal_lahir: String?,
    val jenis_kelamin: String?,
    val alamat: String?
)
