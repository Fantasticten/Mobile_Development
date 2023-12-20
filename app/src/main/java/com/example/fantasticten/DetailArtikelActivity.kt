package com.example.fantasticten

import android.content.Context
import android.content.SharedPreferences
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

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artikel)

        val backButton: ImageView = findViewById(R.id.backArtikel)
        backButton.setOnClickListener { onBackPressed() }

        val artikelId = intent.getStringExtra("ARTIKEL_ID")
        val artikel: ArtikelModel? = artikelId?.let { ArtikelRepository.getArtikelById(it) }

        artikel?.let { displayArtikel(it) }
    }

    private fun displayArtikel(artikel: ArtikelModel) {
        val imageArtikel: ImageView = findViewById(R.id.imageAr)
        val judulTextView: TextView = findViewById(R.id.text_judul_artikel)
        val isiTextView: TextView = findViewById(R.id.text_isi_artikel)
        val penulisTextView: TextView = findViewById(R.id.text_penulis)

        imageArtikel.setImageResource(artikel.gambarResourceId)
        judulTextView.text = artikel.judul
        isiTextView.text = resources.getString(artikel.isiResourceId)
        penulisTextView.text = artikel.penulis
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail_artikel)
//
//        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
//
//        imageViewArtikel = findViewById(R.id.imageAr)
//        textJudulArtikel = findViewById(R.id.text_judul_artikel)
//        textPenulis = findViewById(R.id.text_penulis)
//        textIsiArtikel = findViewById(R.id.text_isi_artikel)
//
//
//        val artikelId = intent.getStringExtra("artikel_id")
//
//        fetchArtikelDetails(artikelId)
//    }

//    private fun showToast(message: String) {
//        Toast.makeText(this@DetailArtikelActivity, message, Toast.LENGTH_SHORT).show()
//    }
//
//    private fun fetchArtikelDetails(artikelId: String?) {
//
//        val token = sharedPreferences.getString("token", "")
//        val apiService = APIService.getService(token)
//        GlobalScope.launch(Dispatchers.Main) {
//            try {
//                val response = apiService.getDetailArtikel(artikelId.orEmpty())
//                if (response.isSuccessful) {
//                    val artikel = response.body()
//
//
//                    artikel?.let {
//                        Glide.with(this@DetailArtikelActivity)
//                            .load("https://keydentalcare.isepwebtim.my.id/img/${it.gambar_artikel}")
//                            .placeholder(R.drawable.artike1)
//                            .error(R.drawable.program)
//                            .into(imageViewArtikel)
//
//                        textJudulArtikel.text = it.judul_artikel
//                        textPenulis.text = "By ${it.penulis}"
//                        textIsiArtikel.text = it.isi_artikel
//                    }
//                } else {
//
//                    showToast("Gagal mengambil detail artikel")
//                }
//            } catch (e: Exception) {
//
//                e.printStackTrace()
//            }
//        }
//    }
}