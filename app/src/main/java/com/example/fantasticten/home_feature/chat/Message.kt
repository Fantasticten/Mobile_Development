package com.example.fantasticten.home_feature.chat

data class Message(
    val text: String,
    val senderId: Int,
    val messageType: MessageType,
    val timestamp: String
)
