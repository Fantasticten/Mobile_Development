package com.example.fantasticten.data

import com.google.gson.annotations.SerializedName

data class ResponseNotif(
	@field:SerializedName("notifikasi")
	val notifikasi: List<NotifikasiItem?>? = null
)

data class NotifikasiItem(
	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("isi_notifikasi")
	val isiNotifikasi: String? = null,

	@field:SerializedName("waktu_pengiriman")
	val waktuPengiriman: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
