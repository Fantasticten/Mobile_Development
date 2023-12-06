package com.example.fantasticten.data

data class DoctorResponse(val doctors: List<Doctor>)

data class Doctor(
    val id: Int,
    val nama_dokter: String,
    val spesialisasi: String,
    val jadwal_praktek: String,
    val info_klinik: String,
    val img_dokter: String,
    val createdAt: String,
    val updatedAt: String
)


