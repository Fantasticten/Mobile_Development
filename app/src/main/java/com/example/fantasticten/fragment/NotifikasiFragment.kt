package com.example.fantasticten.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fantasticten.R
import com.example.fantasticten.adapter.NotifAdapter
import com.example.fantasticten.data.ResponseNotif
import com.example.fantasticten.utils.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifikasiFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var notifAdapter: NotifAdapter
    private lateinit var emptyStateLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifikasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        emptyStateLayout = view.findViewById(R.id.emptyStateNotif)
        recyclerView = view.findViewById(R.id.recyclerviewNotif)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        notifAdapter = NotifAdapter(emptyList()) { itemId ->
            navigateToDetail(itemId)
        }
        recyclerView.adapter = notifAdapter

        fetchNotifikasiData()
    }


    private fun fetchNotifikasiData() {
        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        val userId = sharedPreferences.getInt("user_id", 1)

        val apiService = APIService.getService(token)
        val call = apiService.getNotifikasi(userId)

        call.enqueue(object : Callback<ResponseNotif> {
            override fun onResponse(call: Call<ResponseNotif>, response: Response<ResponseNotif>) {
                if (response.isSuccessful) {
                    val data = response.body()?.notifikasi

                    if (data.isNullOrEmpty()) {
                        showEmptyStateLayout()
                    } else {
                        data.let {
                            notifAdapter.updateData(it.filterNotNull())
                        }
                    }
                } else {
                    showEmptyStateLayout()
                }
            }

            override fun onFailure(call: Call<ResponseNotif>, t: Throwable) {
                Log.e("NotifikasiFragment", "API call failed: ${t.message}")
            }
        })

    }

    private fun showEmptyStateLayout() {
        emptyStateLayout.visibility = View.VISIBLE
    }

    private fun navigateToDetail(itemId: Int) {

    }
}
