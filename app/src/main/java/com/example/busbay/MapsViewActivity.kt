package com.example.busbay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.busbay.databinding.ActivityMapsViewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class MapsViewActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsViewBinding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera



        auth= Firebase.auth
        var firstTimeLocation=1
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        var getdata= object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb=StringBuilder()
                val printobjt = snapshot.child("bcBGd3y4kgS2isAAB8FfiGHzV242").getValue().toString()
                var ltt=""
                var lgg=""
                var flag=0
                for (i in 0..printobjt.length-1) {
                    if(printobjt[i]=='-'){
                        flag=1
                    }
                    else if(flag==1){
                        lgg+=printobjt[i]
                    }
                    else{

                        ltt+=printobjt[i]
                    }
                    println(printobjt[i])
                }
//                Toast.makeText(this@MapsActivity,"ltt"+ltt+" lgg"+lgg ,Toast.LENGTH_SHORT).show()
                mMap.clear()
                val sydney = LatLng(ltt.toDouble(),lgg.toDouble())


                mMap.addMarker(MarkerOptions().position(sydney).title("Bus Location"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));





                if(firstTimeLocation==1){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f))
                    firstTimeLocation=0
                }





            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        myRef.addValueEventListener(getdata)
        myRef.addListenerForSingleValueEvent(getdata)

    }

}