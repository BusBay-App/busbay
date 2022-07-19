package com.example.busbay


import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationRequest
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.*

class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var auth: FirebaseAuth

    private val locationRequest: com.google.android.gms.location.LocationRequest = create().apply {
        interval = 3000
        fastestInterval = 3000
        priority = PRIORITY_BALANCED_POWER_ACCURACY
        maxWaitTime = 5000
    }

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                val location = locationList.last()

                Toast.makeText(this@LocationService, "Latitude: " + location.latitude.toString() + '\n' +
                        "Longitude: "+ location.longitude, Toast.LENGTH_LONG).show()
//                printlocation(location.latitude.toString(),location.longitude.toString())

                val currentUser= auth.currentUser?.uid.toString()

                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("message")
//                myRef.child(currentUser).setValue("la"+ location.latitude.toString() +" lo"+ location.longitude.toString()).addOnSuccessListener {

                myRef.child(currentUser).setValue( location.latitude.toString() +"-"+ location.longitude.toString()).addOnSuccessListener {
                    Log.i("Baground","latitude and long updated")

                }.addOnFailureListener{
                    Log.i("Baground","latitude and long update failed")

                }
                Log.d("Location d", location.latitude.toString())
                Log.i("Location i", location.latitude.toString())
            }
        }
    }

    /*******************/

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        auth= Firebase.auth


        Log.i("Baground","Entered OnCreate in servoce")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) createNotificationChanel() else startForeground(1, Notification())

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(applicationContext, "Permission required", Toast.LENGTH_LONG).show()
            return
        }else{
            fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel() {
        val notificationChannelId = "Location channel id"
        val channelName = "Background Service"
        val chan = NotificationChannel(notificationChannelId, channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder =
            NotificationCompat.Builder(this, notificationChannelId)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setContentTitle("Location updates:")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(2, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}

//private fun MainActivity.Companion.printlocation(lat: String,lng: String) {
////    changetext(lat,lng)
////    MainActivity.(lat,lng)
////    MainActivity.
//
//}
