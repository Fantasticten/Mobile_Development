package com.example.fantasticten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.fantasticten.utils.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailArtikelActivity : AppCompatActivity() {
    private lateinit var imageViewArtikel: ImageView
    private lateinit var textJudulArtikel: TextView
    private lateinit var textPenulis: TextView
    private lateinit var textIsiArtikel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artikel)

        // Inisialisasi view
        imageViewArtikel = findViewById(R.id.imageAr)
        textJudulArtikel = findViewById(R.id.text_judul_artikel)
        textPenulis = findViewById(R.id.text_penulis)
        textIsiArtikel = findViewById(R.id.text_isi_artikel)

        // Ambil ID artikel dari intent
        val artikelId = intent.getStringExtra("artikel_id")

        // Ambil detail artikel dari API
        fetchArtikelDetails(artikelId)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@DetailArtikelActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun fetchArtikelDetails(artikelId: String?) {
        // Gunakan Retrofit untuk mengambil detail artikel berdasarkan artikelId
        val apiService = APIService.getService()
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = apiService.getDetailArtikel(artikelId.orEmpty())
                if (response.isSuccessful) {
                    val artikel = response.body()

                    // Perbarui UI dengan detail artikel
                    artikel?.let {
                        Glide.with(this@DetailArtikelActivity)
                            .load("https://keydentalcare.isepwebtim.my.id/img/${it.gambar_artikel}")
                            .placeholder(R.drawable.artike1)
                            .error(R.drawable.program)
                            .into(imageViewArtikel)

                        textJudulArtikel.text = it.judul_artikel
                        textPenulis.text = "By ${it.penulis}"
                        textIsiArtikel.text = it.isi_artikel
                    }
                } else {
                    // Tangani kesalahan
                    showToast("Gagal mengambil detail artikel")
                }
            } catch (e: Exception) {
                // Tangani pengecualian
                e.printStackTrace()
            }
        }
    }
}