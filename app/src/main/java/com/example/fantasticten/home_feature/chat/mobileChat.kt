package com.example.fantasticten.home_feature.chat

data class mobileChat(

    val senderId: String?,
    val chatMobile: String,
    val chatImage: String
){
    constructor(): this("","",""){

    }
}
