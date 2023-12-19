package com.example.fantasticten.data

data class editProfil(
    val id: Int,
    val titleName: String,
    val TanggalLahir: String,
    val jenisKelamin: String,
    val No_telp: String,
    val Email: String,
    val Alamat: String
)
data class user(val programs: List<editProfil>)
