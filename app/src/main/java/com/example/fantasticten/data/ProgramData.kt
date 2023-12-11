package com.example.fantasticten.data

data class ProgramData(
    val id: Int,
    val nama_program: String,
    val deskripsi_program: String,
    val harga_program: String,
    val thumbnail: String
)
data class ProgramResponse(val programs: List<ProgramData>)