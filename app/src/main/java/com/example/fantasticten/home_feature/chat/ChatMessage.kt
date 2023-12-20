package com.example.fantasticten.home_feature.chat

data class ChatMessage(
    val message: String,
    val senderId: Int,
    val messageType: MessageType,
    val timestamp: String
)
