package com.example.fantasticten.data

import com.google.gson.annotations.SerializedName

data class ResponseRiwayat(
	@SerializedName("completedPatients")
	val completedPatients: List<CompletedPatientsItem>? = null
)

data class CompletedPatientsItem(
	@SerializedName("createdAt")
	val createdAt: String? = null,

	@SerializedName("hari_tanggal")
	val hariTanggal: String? = null,

	@SerializedName("kode_antrian")
	val kodeAntrian: String? = null,

	@SerializedName("layanan")
	val layanan: String? = null,

	@SerializedName("nama")
	val nama: String? = null,

	@SerializedName("jam_kunjungan")
	val jamKunjungan: String? = null,

	@SerializedName("user_id")
	val userId: Int? = null,

	@SerializedName("nomor_telepon")
	val nomorTelepon: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("email")
	val email: Any? = null,

	@SerializedName("status")
	val status: String? = null,

	@SerializedName("updatedAt")
	val updatedAt: String? = null
)
