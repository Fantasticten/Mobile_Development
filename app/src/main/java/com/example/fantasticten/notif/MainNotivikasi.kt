package com.example.fantasticten.notif

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fantasticten.R
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class MainNotivikasi : AppCompatActivity() {

    private var socket: Socket? = null
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 123
    private val CHANNEL_ID = "my_channel_id"
    private val NOTIFICATION_ID = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                Toast.makeText(this@MainNotivikasi, "Connected to server", Toast.LENGTH_SHORT).show()
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
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifikasi)
            .setContentTitle("Judul Notifikasi")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

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
                    this@MainNotivikasi,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notify(NOTIFICATION_ID, notificationBuilder.build())
            } else {
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
                    showNotification("Pendaftaran Anda Telah di Terima")
                }
            } else {

                Toast.makeText(this, "Izin notifikasi ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
