package com.example.fantasticten.home_feature.chat

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.fantasticten.R


class ChatAktivity : AppCompatActivity() {
    private lateinit var editText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_aktivity)
        editText = findViewById(R.id.editTextChat)

    }
}