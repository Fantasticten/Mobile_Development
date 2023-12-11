package com.example.fantasticten.data

data class ArtikelData(
    val id: String,
    val judul_artikel: String,
    val isi_artikel: String,
    val tanggal_publikasi: String,
    val penulis: String,
    val gambar_artikel: String,
)
data class ArtikelResponse(val articles: List<ArtikelData>)