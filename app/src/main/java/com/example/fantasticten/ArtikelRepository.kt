package com.example.fantasticten

object ArtikelRepository {
    private val artikelData: Map<String, ArtikelModel> = mapOf(
        "0" to ArtikelModel("1", "Berikut ini adalah cara mencegah gigi berlubang pada anak-anak", "By Dokter Gigi", R.string.artikel, R.drawable.imageartikel),
        "1" to ArtikelModel("2", "Apa penyebab gigi anda copot? Berikut ini adalah cara mengatasi nya", "By Dokter Gigi", R.string.artikel2, R.drawable.imageartikel2),
        "2" to ArtikelModel("3", "Berikut ini adalah cara untuk mengatasi gigi kuning", "By Dokter Gigi", R.string.artikel3, R.drawable.imageartikel3),

    )

    fun getArtikelById(id: String): ArtikelModel? {
        return artikelData[id]
    }
}