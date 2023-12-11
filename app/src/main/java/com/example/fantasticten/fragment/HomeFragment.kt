package com.example.fantasticten.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.fantasticten.DetailArtikelActivity
import com.example.fantasticten.LokasiActivity
import com.example.fantasticten.R
import com.example.fantasticten.adapter.ArtikelAdapter
import com.example.fantasticten.data.ArtikelData
import com.example.fantasticten.home_feature.*
import com.example.fantasticten.iklan_item
import com.example.fantasticten.utils.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var artikelAdapter: ArtikelAdapter
    private val apiService = APIService.getService()
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




        return next
    }
    private var artikelList: MutableList<ArtikelData> = mutableListOf()

    private fun setdataList() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getartic()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        artikelList.clear()
                        artikelList.addAll(response.body()?.articles ?: emptyList())
                        artikelAdapter.notifyDataSetChanged()
                    } else {
                        showToast("Failed to fetch artikel data from API")
                    }
                }
            } catch (e: Exception) {
                showToast("An error occurred: ${e.message}")
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recyle2)
        recyclerView.layoutManager = LinearLayoutManager(context)
        artikelAdapter = ArtikelAdapter(requireContext(), artikelList)
        recyclerView.adapter = artikelAdapter


        // Load data
        setdataList()

    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun startDetailActivity(artikelId: String) {
        val intent = Intent(requireContext(), DetailArtikelActivity::class.java)
        intent.putExtra("ARTIKEL_ID", artikelId)
        startActivity(intent)
    }


}