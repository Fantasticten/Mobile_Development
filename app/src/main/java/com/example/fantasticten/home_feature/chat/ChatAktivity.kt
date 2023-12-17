package com.example.fantasticten.home_feature.chat

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.example.fantasticten.databinding.ActivityChatAktivityBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ChatAktivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatAktivityBinding
    private  var firebaseDatabase: FirebaseDatabase? = null
    private  var databaseReference: DatabaseReference?= null
    private lateinit var ref : DatabaseReference
    private lateinit var ref1 : DatabaseReference
    private lateinit var cList : ArrayList<mobileChat>
    private lateinit var cList1 : ArrayList<mobileChatLeft>
    private lateinit var adapterchat: adapterChat
    private lateinit var adapterchat1: adapterChatLeft
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatAktivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView =findViewById(R.id.tampilchat)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        val recyc = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = recyc
        recyc.stackFromEnd=true


        val galery =registerForActivityResult(ActivityResultContracts.GetContent(),
            ActivityResultCallback {

            })
        binding.imageButton2.setOnClickListener{
            galery.launch("image/*")
        }
        ref = FirebaseDatabase.getInstance().getReference("mobile")

        cList = arrayListOf()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    cList.clear()
                    for (h in snapshot.children){
                        val mobile = h.getValue(mobileChat::class.java)
                        cList.add(mobile!!)

                    }
                    recyclerView.adapter = adapterChat(cList!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        binding.imageButton.setOnClickListener{

            saveData()
            binding.editTextChat.text.clear()

        }


    }


    private fun saveData(){
        val chatMobile = binding.editTextChat.text.toString().trim()

        val  chatId = ref.push().key
        val ChatMobile = mobileChat(chatId,chatMobile)
        if ( chatId != null){
            ref.child(chatId).setValue(ChatMobile).addOnCompleteListener{
                Toast.makeText(applicationContext,"pesan berhasil di kirim",Toast.LENGTH_SHORT).show()
            }
        }
    }
}