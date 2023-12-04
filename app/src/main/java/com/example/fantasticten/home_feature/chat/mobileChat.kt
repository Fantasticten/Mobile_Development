package com.example.fantasticten.home_feature.chat

data class mobileChat(
    val chatId :String?,
    val chatMobile : String
){
    constructor(): this("", ""){

    }
}
