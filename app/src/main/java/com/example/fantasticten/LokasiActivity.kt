package com.example.fantasticten

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class LokasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lokasi)

        val cardView: ImageView = findViewById(R.id.lokasiGo)
        cardView.setOnClickListener {
            showLocationConfirmationDialog()
        }
    }

    private fun showLocationConfirmationDialog() {
        val builder = AlertDialog.Builder(this@LokasiActivity)
        builder.setMessage("Izinkan Aplikasi untuk melihat lokasi?")
            .setPositiveButton("Ya") { dialog, which ->
                openGoogleMaps()
            }
            .setNegativeButton("Tidak") { dialog, which ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun openGoogleMaps() {
        val alamatTujuan = "KeidentalCare, Jl. Sentot Ali Basa No.36, Jati Mudik, Kec. Pariaman Tengah, Kota Pariaman, Sumatera Barat 25519"
        val uri = "http://maps.google.com/maps?saddr=&daddr=$alamatTujuan"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
