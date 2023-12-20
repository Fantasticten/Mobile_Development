package com.example.fantasticten.data

import com.google.gson.annotations.SerializedName

data class ResponseKunjung(

	@field:SerializedName("approvedPatients")
	val approvedPatients: ApprovedPatients? = null
)

data class ApprovedPatients(

	@field:SerializedName("rekam_medis_id")
	val rekamMedisId: Any? = null,

	@field:SerializedName("hari_tanggal")
	val hariTanggal: String? = null,

	@field:SerializedName("kode_antrian")
	val kodeAntrian: String? = null,

	@field:SerializedName("layanan")
	val layanan: String? = null,

	@field:SerializedName("jam_kunjungan")
	val jamKunjungan: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("nomor_telepon")
	val nomorTelepon: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: Any? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

