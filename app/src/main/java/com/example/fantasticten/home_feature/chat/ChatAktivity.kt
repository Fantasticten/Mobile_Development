package com.example.fantasticten.home_feature.chat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.example.fantasticten.databinding.ActivityChatAktivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.ByteArrayOutputStream


class ChatAktivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatAktivityBinding
    private lateinit var ref : DatabaseReference
    private lateinit var cList : ArrayList<mobileChat>
    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedPreferences: SharedPreferences
    var firebaseUser : FirebaseUser?=null
    var simage : String = ""
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
        recyclerView.smoothScrollToPosition(0)
        binding.imageView7.bringToFront()

        sharedPreferences= getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val index = username
        binding.imageButton2.setOnClickListener{
            selectimage()
        }
        firebaseUser = FirebaseAuth.getInstance().currentUser
        ref = FirebaseDatabase.getInstance().getReference("$index/chat")
        cList = arrayListOf()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    cList.clear()
                    for (k in snapshot.children){
                            val mobil = k.getValue(mobileChat::class.java)
                            cList.add(mobil!!)
                    }
                    recyclerView.adapter = adapterChat(cList!!)

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        binding.imageButton.setOnClickListener{

            saveData()
            simage = ""
            binding.imageView7.setImageBitmap(null)
            binding.editTextChat.text.clear()

        }


    }


    private fun selectimage() {
        val intent  = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            val uri = data?.data!!
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
                val byte = stream.toByteArray()
                simage =android.util.Base64.encodeToString(byte,android.util.Base64.DEFAULT)
                binding.imageView7.setImageBitmap(myBitmap)
                inputStream!!.close()
            }
            catch(e : Exception){
                Toast.makeText(this, "err", Toast.LENGTH_SHORT).show()
            }


        }
    }


    private fun saveData(){
        val chatMobile1 = binding.editTextChat.text.toString().trim()
        val  senderId =  ref.push().key
        val  chaid=  ref.push().key
        val ChatMobile1 = mobileChat(senderId,chatMobile1,simage)
        if ( chaid != null){
            ref.child(chaid).setValue(ChatMobile1).addOnCompleteListener{
                Toast.makeText(applicationContext,"pesan berhasil di kirim",Toast.LENGTH_SHORT).show()
            }
        }
    }


    }