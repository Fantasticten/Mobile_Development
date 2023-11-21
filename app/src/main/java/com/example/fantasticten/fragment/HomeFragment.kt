package com.example.fantasticten.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.fantasticten.R
import com.example.fantasticten.adapter.iklan_adapter
import com.example.fantasticten.home_feature.*
import com.example.fantasticten.iklan_item

class HomeFragment : Fragment() {
    private lateinit var newsarrayList: ArrayList<iklan_item>
    private lateinit var recyclerView: RecyclerView
    private lateinit var iklanAdapter: iklan_adapter
    lateinit var imageid :Array<Int>
    lateinit var tulis :Array<String>
    lateinit var iklan2 :Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val next= inflater.inflate(R.layout.fragment_home, container, false)
        val imageSlider = next.findViewById<ImageSlider>(R.id.image_slider)
        val konsultasi = next.findViewById<ImageButton>(R.id.konsultasi)
        val program = next.findViewById<ImageButton>(R.id.program)
        val tretment = next.findViewById<ImageButton>(R.id.treatment)
        val daftarAntrian = next.findViewById<ImageButton>(R.id.dftrantrian)
        val dokter = next.findViewById<ImageButton>(R.id.doktor)
        val lokasi = next.findViewById<ImageButton>(R.id.lokasi)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.slide1))
        imageList.add(SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUDsuqzb99RjFg0oi-3fWH5D0Dz2Q82Y2GDg&usqp=CAU"))
        imageList.add(SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkQ5UYCFYLZUL71cWks1JTt7pujkfQYHXDnA&usqp=CAU"))
        imageList.add(SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRtCuvnsFV_MmWxB65QScMbQDW4kqDD26j0pA&usqp=CAU"))
        imageSlider.setImageList(imageList,ScaleTypes.FIT)

        daftarAntrian.setOnClickListener{
            val intent = Intent(activity,Daftar_Antrian::class.java)
            startActivity(intent)
        }
        program.setOnClickListener{
            val intent = Intent(activity,Program::class.java)
            startActivity(intent)
        }
        konsultasi.setOnClickListener{
            val intent = Intent(activity,Konsultasi::class.java)
            startActivity(intent)

        tretment.setOnClickListener{
            val intent = Intent(activity,Treatment::class.java)
            startActivity(intent)
        }
        dokter.setOnClickListener{
            val intent = Intent(activity,Dokter::class.java)
            startActivity(intent)
        }}




        return next
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setdataList()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyle2)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        iklanAdapter = iklan_adapter(newsarrayList)
        recyclerView.adapter=iklanAdapter
    }


    private fun setdataList() {

       newsarrayList = arrayListOf<iklan_item>()
        imageid = arrayOf(
            R.drawable.iklan,
            R.drawable.iklan,
            R.drawable.iklan,
            R.drawable.iklan,
            R.drawable.iklan,
            R.drawable.iklan,
            R.drawable.iklan,
            R.drawable.iklan,
        )
        tulis = arrayOf(
          "Berikut ini adalah beberapa cara mencegah gigi berlubang pada anak-anak",
          "Berikut ini adalah beberapa cara mencegah gigi berlubang pada anak-anak",
          "Berikut ini adalah beberapa cara mencegah gigi berlubang pada anak-anak",
          "Berikut ini adalah beberapa cara mencegah gigi berlubang pada anak-anak",
          "Berikut ini adalah beberapa cara mencegah gigi berlubang pada anak-anak",
          "Berikut ini adalah beberapa cara mencegah gigi berlubang pada anak-anak",
          "Berikut ini adalah beberapa cara mencegah gigi berlubang pada anak-anak",
          "Berikut ini adalah beberapa cara mencegah gigi berlubang pada anak-anak",

        )
        for (i in imageid.indices){
            val news = iklan_item(imageid[i],tulis[i])
            newsarrayList.add(news)
        }

    }
}