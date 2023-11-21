package com.example.fantasticten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailArtikelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artikel)

        val backButton: ImageView = findViewById(R.id.backArtikel)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val artikelId = intent.getStringExtra("ARTIKEL_ID")
        val artikel: ArtikelModel? = artikelId?.let { ArtikelRepository.getArtikelById(it) }

        artikel?.let {
            val imageArtikel: ImageView = findViewById(R.id.imageAr)
            val judulTextView: TextView = findViewById(R.id.text_judul_artikel)
            val isiTextView: TextView = findViewById(R.id.text_isi_artikel)
            val penulisTextView: TextView = findViewById(R.id.text_penulis)

            imageArtikel.setImageResource(artikel.gambarResourceId)
            judulTextView.text = artikel.judul
            isiTextView.text = resources.getString(artikel.isiResourceId)
            penulisTextView.text = artikel.penulis
        }
    }
}