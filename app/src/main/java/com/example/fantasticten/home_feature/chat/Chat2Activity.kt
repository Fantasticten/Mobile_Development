package com.example.fantasticten.home_feature.chat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.fantasticten.R
import com.example.fantasticten.utils.APIService
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class Chat2Activity : AppCompatActivity() {

    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var attachButton: ImageButton
    private lateinit var chatListView: ListView
    private lateinit var adapter: ChatAdapter
    private lateinit var socket: Socket
    private lateinit var sharedPreferences: SharedPreferences

    private val messages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat2)

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)
        attachButton = findViewById(R.id.attachButton)
        chatListView = findViewById(R.id.chatListView)

        fetchChatHistory()

        try {
            socket = IO.socket("http://103.171.85.30:4000")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        adapter = ChatAdapter(this, R.layout.message_item, messages)
        chatListView.adapter = adapter

        socket.on(Socket.EVENT_CONNECT) {
            Log.d("Socket.IO", "Socket connected")
        }.on(Socket.EVENT_DISCONNECT) {
            Log.d("Socket.IO", "Socket disconnected")
        }.on(Socket.EVENT_CONNECT_ERROR) {
            Log.e("Socket.IO", "Socket connect error: ${it[0]}")
        }


        socket.connect()


        socket.on("receiveMessage") { args ->
            runOnUiThread {
                val data = args[0] as JSONObject
                val message = data.getString("message")
                val senderId = data.getInt("senderId")
                val timestamp = data.getString("timestamp")
                val messageType = MessageType.TEXT
                messages.add(Message(message, senderId, messageType, timestamp))
                adapter.notifyDataSetChanged()
            }
        }

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString().trim()
            if (message.isNotEmpty()) {

                val userId = sharedPreferences.getInt("user_id", 1)

                val senderId = userId
                val receiverId = 13
                val jsonObject = JSONObject()
                jsonObject.put("message", message)
                jsonObject.put("senderId", senderId)
                jsonObject.put("receiverId", receiverId)
                socket.emit("sendMessage", jsonObject)
                messageEditText.text.clear()
            }
        }

        attachButton.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }

    private fun fetchChatHistory() {
        val token = sharedPreferences.getString("token", "")
        val userId = sharedPreferences.getInt("user_id", 1)

        val apiService = APIService.getService(token)
        val call = apiService.getChatHistory(userId)

        call.enqueue(object : retrofit2.Callback<List<ChatMessage>> {
            override fun onResponse(call: Call<List<ChatMessage>>, response: Response<List<ChatMessage>>) {
                if (response.isSuccessful) {
                    val chatData = response.body()
                    chatData?.forEach { message ->
                        val messageType = message.messageType ?: MessageType.TEXT
                        messages.add(
                            Message(
                                message.message,
                                message.senderId,
                                messageType,
                                message.timestamp.toString()
                            )
                        )
                    }

                    adapter.notifyDataSetChanged()
                } else {

                }
            }

            override fun onFailure(call: Call<List<ChatMessage>>, t: Throwable) {

            }
        })
    }

}
