package com.example.fantasticten

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.chaos.view.PinView

class Verifikasi : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifikasi)

        val pinView: PinView = findViewById(R.id.pinView)

        pinView.requestFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(pinView, InputMethodManager.SHOW_IMPLICIT)

        pinView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 4) {
                    val toast = Toast.makeText(applicationContext, "OTP Berhasil Di Verifikasi", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()

                    Handler().postDelayed({
                        val intent = Intent(this@Verifikasi, Ganti_Sandi::class.java)
                        startActivity(intent)
                    }, 2000)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}