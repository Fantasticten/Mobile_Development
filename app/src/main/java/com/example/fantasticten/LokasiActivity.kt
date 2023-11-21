package com.example.fantasticten

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class LokasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lokasi)

        val cardView: MaterialCardView = findViewById(R.id.cardView)
        cardView.setOnClickListener {
            val builder = AlertDialog.Builder(this@LokasiActivity)
            builder.setMessage("Izinkan Aplikasi untuk melihat lokasi?")
                .setPositiveButton("Ya") { dialog, which ->
                    val alamatTujuan = "JL+Sentot+Ali+Basa+No.36,+Jati+Mudik,+Kec.Pariaman+Tengah,+Kota+Pariaman,+Sumatera+Barat"
                    val uri = "http://maps.google.com/maps?saddr=&daddr=$alamatTujuan"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    intent.setPackage("com.google.android.apps.maps")
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    }
                }
                .setNegativeButton("Tidak") { dialog, which ->
                    dialog.dismiss()
                }
            val dialog = builder.create()
            dialog.show()
        }
    }
}
