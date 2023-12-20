package com.example.fantasticten

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class ChatBotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)

        val webView: WebView = findViewById(R.id.webViewInFragment)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.setBackgroundColor(Color.TRANSPARENT)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (!url.isNullOrEmpty()) {
                    view?.loadUrl(url)
                }
                return true
            }
        }

        webView.loadUrl("https://keydentalcare.isepwebtim.my.id/chatBot.html")
        webView.visibility = View.INVISIBLE

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.visibility = View.VISIBLE
            }
        }
    }
}
