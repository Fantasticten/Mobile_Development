package com.example.fantasticten

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fantasticten.databinding.ActivityMainBinding
import com.example.fantasticten.fragment.HomeFragment
import com.example.fantasticten.fragment.JadwalFragment
import com.example.fantasticten.fragment.NotifikasiFragment
import com.example.fantasticten.fragment.ProfilFragment
import com.example.fantasticten.utils.NetworkUtils
import com.google.android.material.navigation.NavigationBarView
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val handler = Handler(Looper.getMainLooper())
    private val checkDelay: Long = 7000
    private var isAlertShowing = false
    private var socket: Socket? = null
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 123
    private val CHANNEL_ID = "my_channel_id"
    private val NOTIFICATION_ID = 101

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val buttonImage: ImageView? = binding.buttonImage
        var isMoved = false

        buttonImage?.setOnTouchListener { v, event ->

            val parentWidth = (v.parent as View).width
            val parentHeight = (v.parent as View).height
            val buttonWidth = v.width
            val buttonHeight = v.height

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val dx = event.rawX - v.x
                    val dy = event.rawY - v.y
                    v.tag = Pair(dx, dy)
                    isMoved = false
                }
                MotionEvent.ACTION_MOVE -> {
                    val tag = v.tag as? Pair<Float, Float>
                    val dx = tag?.first ?: 0f
                    val dy = tag?.second ?: 0f
                    var x = event.rawX - dx
                    var y = event.rawY - dy

                    if (x < 0) x = 0f
                    if (y < 0) y = 0f
                    if (x > parentWidth - buttonWidth) x = (parentWidth - buttonWidth).toFloat()
                    if (y > parentHeight - buttonHeight) y = (parentHeight - buttonHeight).toFloat()

                    v.x = x
                    v.y = y

                    isMoved = true
                }
                MotionEvent.ACTION_UP -> {

                    if (!isMoved) {
                        val intent = Intent(this@MainActivity, ChatBotActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            true
        }

        checkInternetConnection()

        binding.bottomBar.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomBar.itemIconTintList = createColorStateList()
        binding.bottomBar.itemTextColor = createColorStateList()

        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.jadwal -> replaceFragment(JadwalFragment())
                R.id.notifikasi -> replaceFragment(NotifikasiFragment())
                R.id.profil -> replaceFragment(ProfilFragment())
                else -> {}
            }
            true
        }

        binding.frameLayout.setOnTouchListener(hide_nav(this, binding.bottomBar))

        val sharedPreferences = getSharedPreferences("NavigationPrefs", Context.MODE_PRIVATE)
        val isProfileNavigationNeeded = sharedPreferences.getBoolean("notificationNavigationNeeded", false)

        if (isProfileNavigationNeeded) {
            binding.bottomBar.selectedItemId = R.id.notifikasi
            sharedPreferences.edit().putBoolean("notificationNavigationNeeded", false).apply()
        } else {
            binding.bottomBar.selectedItemId = R.id.home
        }

        try {
            val opts = IO.Options()
            opts.forceNew = true
            socket = IO.socket("http://103.171.85.30:4000")
            socket?.connect()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        socket?.on(Socket.EVENT_CONNECT) {
            runOnUiThread {
                Toast.makeText(this@MainActivity, "Connected to server", Toast.LENGTH_SHORT).show()
            }
        }

        socket?.on("notification") { args ->
            val data = args[0] as JSONObject
            val notificationMessage = data.getString("isi_notifikasi")
            showNotification(notificationMessage)
        }

        requestNotificationPermission()

    }

    @SuppressLint("ObsoleteSdkInt")
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = arrayOf(
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.VIBRATE
            )

            val permissionDeniedList = permissions.filter {
                ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            }

            if (permissionDeniedList.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    permissionDeniedList.toTypedArray(),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun showNotification(message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifikasi)
            .setContentTitle("Konfirmasi Pendaftaran Pasien")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // Tambahkan PendingIntent ke notifikasi

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel"
            val descriptionText = "Deskripsi Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Izin belum diberikan, tampilkan notifikasi tanpa melakukan tindakan
                notify(NOTIFICATION_ID, notificationBuilder.build())
            } else {
                // Izin telah diberikan, tampilkan notifikasi
                notify(NOTIFICATION_ID, notificationBuilder.build())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket?.disconnect()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.RECEIVE_BOOT_COMPLETED
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    showNotification("Pendaftaran Anda Telah di Teriama ")
                }
            } else {
                Toast.makeText(this, "Izin notifikasi ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createColorStateList(): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        )
        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.hijau),
            ContextCompat.getColor(this, R.color.abu)
        )
        return ColorStateList(states, colors)
    }

    override fun onResume() {
        super.onResume()
        startCheckingInternet()
    }

    override fun onPause() {
        super.onPause()
        stopCheckingInternet()
    }

    private fun checkInternetConnection() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showNoInternetDialog()
        } else {
            if (isAlertShowing) {
                isAlertShowing = false
            }
        }
    }

    private fun showNoInternetDialog() {
        if (!isAlertShowing) {
            isAlertShowing = true
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Tidak Ada Koneksi Internet")
            builder.setMessage("Mohon periksa koneksi internet Anda dan coba lagi.")
            builder.setIcon(R.drawable.no_internet)
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                isAlertShowing = false
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }


    private fun startCheckingInternet() {
        handler.postDelayed(checkInternetRunnable, checkDelay)
    }

    private fun stopCheckingInternet() {
        handler.removeCallbacks(checkInternetRunnable)
    }

    private val checkInternetRunnable = object : Runnable {
        override fun run() {
            checkInternetConnection()
            handler.postDelayed(this, checkDelay)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}

