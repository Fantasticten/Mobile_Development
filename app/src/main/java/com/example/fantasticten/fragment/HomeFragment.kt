package com.example.fantasticten.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.fantasticten.DetailArtikelActivity
import com.example.fantasticten.LokasiActivity
import com.example.fantasticten.R
import com.example.fantasticten.adapter.iklan_adapter
import com.example.fantasticten.home_feature.*
import com.example.fantasticten.iklan_item
import com.example.fantasticten.view_model.LoginActivityViewModel
import androidx.fragment.app.viewModels
import com.example.fantasticten.MainActivity
import com.example.fantasticten.login
import com.example.fantasticten.view_model.RegisterActivityViewModel

import kotlin.math.log

class HomeFragment : Fragment() {
    private lateinit var newsarrayList: ArrayList<iklan_item>
    private lateinit var recyclerView: RecyclerView
    private lateinit var iklanAdapter: iklan_adapter
    lateinit var imageid :Array<Int>
    lateinit var tulis :Array<String>
    lateinit var iklan2 :Array<String>

    private lateinit var namaUser: TextView
    private lateinit var mViewModel: RegisterActivityViewModel

    private lateinit var loginViewModel: LoginActivityViewModel
    private lateinit var mViewModel2: LoginActivityViewModel

//    private val loginActivityViewModel: LoginActivityViewModel by viewModels()


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
        namaUser = next.findViewById(R.id.namaUser)

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
        konsultasi.setOnClickListener {
            val intent = Intent(activity, Konsultasi::class.java)
            startActivity(intent)
        }

        tretment.setOnClickListener{
            val intent = Intent(activity,Treatment::class.java)
            startActivity(intent)
        }

        dokter.setOnClickListener{
            val intent = Intent(activity,Dokter::class.java)
            startActivity(intent)
        }

        lokasi.setOnClickListener {
            val intent = Intent(requireContext(), LokasiActivity::class.java)
            startActivity(intent)
        }

        mViewModel2 = ViewModelProvider(this).get(LoginActivityViewModel::class.java)

        setUpObservers()

//        var userNamanya = mViewModel2.getUser().value
//        namaUser.text = userNamanya?.username ?: "Data User Error"


        return next
    }


    private fun setUpObservers() {
        // Use mViewModel2 here to observe data or perform other actions
        mViewModel2.getUserLiveData().observe(viewLifecycleOwner) { user ->
            Log.d("HomeFragment", "User data changed: $user")
            namaUser.text = user.username
        }
    }

    private fun setUpRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyle2)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        setdataList()
        iklanAdapter = iklan_adapter(newsarrayList)
        recyclerView.adapter = iklanAdapter

        iklanAdapter.setOnItemClickListener(object : iklan_adapter.OnItemClickListener {
            override fun onItemClick(artikelId: String) {
                startDetailActivity(artikelId)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setdataList()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyle2)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        iklanAdapter = iklan_adapter(newsarrayList)
        recyclerView.adapter = iklanAdapter

        iklanAdapter.setOnItemClickListener(object : iklan_adapter.OnItemClickListener {
            override fun onItemClick(artikelId: String) {
                startDetailActivity(artikelId)
            }
        })
    }



    private fun setdataList() {
        newsarrayList = arrayListOf<iklan_item>()
        imageid = arrayOf(
            R.drawable.imageartikel,
            R.drawable.imageartikel2,
            R.drawable.imageartikel3,
        )
        tulis = arrayOf(
            "Berikut ini adalah beberapa cara mencegah gigi berlubang pada anak-anak",
            "Penyebab gigi copot dan cara mengatasinya",
            "Berikut ini adalah cara mengatasi gigi kuning menurut dokter gigi",
        )
        for (i in imageid.indices){
            val news = iklan_item(imageid[i],tulis[i], i)
            newsarrayList.add(news)
        }
    }

    private fun startDetailActivity(artikelId: String) {
        val intent = Intent(requireContext(), DetailArtikelActivity::class.java)
        intent.putExtra("ARTIKEL_ID", artikelId)
        startActivity(intent)
    }

}