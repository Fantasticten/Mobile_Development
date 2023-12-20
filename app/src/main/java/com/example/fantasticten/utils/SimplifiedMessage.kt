package com.example.fantasticten.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SimplifiedMessage {
    fun get(stringMessage: String): HashMap<String, String> {
        val message = HashMap<String, String>()
        try {
            val jsonObject = Gson().fromJson<HashMap<String, String>>(
                stringMessage,
                object : TypeToken<HashMap<String, String>>() {}.type
            )
            message.putAll(jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return message
    }
}

