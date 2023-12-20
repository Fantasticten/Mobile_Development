package com.example.fantasticten.data

data class TratmentResponse(val treatments: List<dataTreatment>)
data class dataTreatment(
    val id: Int,
    val user_id: Int,
    val nama_layanan: String,
    val deskripsi: String,
    val harga: String,
    val keterangan_tambahan: String,
    val hari_tanggal: String,
    val createdAt: String,
    val updatedAt: String
)