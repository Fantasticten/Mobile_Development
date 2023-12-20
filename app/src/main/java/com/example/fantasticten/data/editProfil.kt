package com.example.fantasticten.data

import com.google.gson.annotations.SerializedName
data class editProfil(
	@SerializedName("user")
	val ususu: useredit? = null

)
data class useredit(
	@SerializedName("createdAt")
	val createdAt: String? = null,
	@SerializedName("password")
	val password: String? = null,
	@SerializedName("profile_photo")
	val profilePhoto: String? = null,
	@SerializedName("phone_number")
	val phoneNumber: String? = null,
	@SerializedName("id")
	val id: Int? = null,
	@SerializedName("jenis_kelamin")
	val jenisKelamin: String? = null,
	@SerializedName("email")
	val email: String? = null,
	@SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null,
	@SerializedName("username")
	val username: String? = null,
	@SerializedName("alamat")
	val alamat: String? = null,
	@SerializedName("updatedAt")
	val updatedAt: String? = null
)