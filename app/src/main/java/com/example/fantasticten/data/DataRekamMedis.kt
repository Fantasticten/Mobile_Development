package com.example.fantasticten.data


data class DataRekamMedis(
    val id: Int,
    val pasien_id: Int,
    val kode_rekam_medis: String,
    val dokter: String,
    val keluhan: String,
    val diagnosa: String,
    val tindakan: String,
    val keterangan: String,
    val tanggal_pemeriksaan: String,
    val createdAt: String,
    val updatedAt: String
)
