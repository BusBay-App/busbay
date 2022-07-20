package com.example.busbay

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.example.busbay.databinding.FragmentMapBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


////



class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var auth: FirebaseAuth
    var mLocationService: LocationService = LocationService()//

    lateinit var startServiceBtn: Button //
    lateinit var mServiceIntent: Intent //
    lateinit var stopServiceBtn: Button //
    lateinit var busTimmingBtn: Button //

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMapBinding.inflate(inflater,container,false)
        //Firebase auth
        auth= Firebase.auth//different UI
        kichaui()

        //visiblity

        val  user_profession=getDefaults("Profession")

        if(user_profession=="Bus Driver"){
            binding.btnMap4user.setVisibility(View.INVISIBLE);
            binding.btnstop.setVisibility(View.INVISIBLE);

        }
        else{
            binding.btnToMap.setVisibility(View.INVISIBLE);
            binding.btnstop.setVisibility(View.INVISIBLE);
//            binding.btnMap4user.setVisibility(View.VISIBLE);

        }



//        binding.btnToMap.setOnClickListener {
//            startActivity(Intent(getActivity(),MapActivity::class.java))  /////////////////////////////////////////////
//
//        }
        binding.btnMap4user.setOnClickListener {
            val intent= Intent(getActivity(),MapsViewActivity::class.java)///
            startActivity(intent)


        }



        ///////////////////////////////////////////////////////////////////////
        startServiceBtn=binding.btnToMap //
        stopServiceBtn=binding.btnstop //
        busTimmingBtn=binding.btnToBusTimming

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")


        var getdata= object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb=StringBuilder()
                val printobjt=snapshot.child("bcBGd3y4kgS2isAAB8FfiGHzV242").getValue()
//                binding.btnoutput.setText(printobjt.toString())
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        myRef.addValueEventListener(getdata)
        myRef.addListenerForSingleValueEvent(getdata)

        //////////////
        startServiceBtn.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                        AlertDialog.Builder(requireActivity()).apply {
                            setTitle("Background permission")
                            setMessage(R.string.background_location_permission_message)
//                            setPositiveButton("Start service anyway",
//                                DialogInterface.OnClickListener { dialog, id ->
//                                    starServiceFunc()
//
//                                })
                            setPositiveButton("Grant background Permission",
                                DialogInterface.OnClickListener { dialog, id ->
                                    requestBackgroundLocationPermission()
                                })
                        }.create().show()

                    }else if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        == PackageManager.PERMISSION_GRANTED){
                        Log.i("Baground","permission active")
                        val intent= Intent(getActivity(),MapsViewActivity::class.java) ///
                        startActivity(intent)
                        starServiceFunc(user_profession)
                    }
                }else{
                    val intent= Intent(getActivity(),MapsViewActivity::class.java)///
                    startActivity(intent)
                    starServiceFunc(user_profession)
                }

            }else if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ///
                    AlertDialog.Builder(requireActivity())
                        .setTitle("ACCESS_FINE_LOCATION")
                        .setMessage("Location permission required")
                        .setPositiveButton(
                            "OK"
                        ) { _, _ ->
                            requestFineLocationPermission()
                        }
                        .create()
                        .show()
                                ////////
//


                } else {
                    requestFineLocationPermission()
                }
            }



        }

        /////////
        stopServiceBtn.setOnClickListener {
            Log.i("Baground","attempt to stop service")


            stopServiceFunc(user_profession)
            val currentUser= auth.currentUser?.uid.toString()

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("message")
//                myRef.child(currentUser).setValue("la"+ location.latitude.toString() +" lo"+ location.longitude.toString()).addOnSuccessListener {

            myRef.child(currentUser).setValue( "Dont share").addOnSuccessListener {
                Log.i("Baground","latitude and long updated")

            }.addOnFailureListener{
                Log.i("Baground","latitude and long update failed")

            }
        }
        /////////
        busTimmingBtn.setOnClickListener {
            val window=PopupWindow(requireActivity())
            val view =layoutInflater.inflate(R.layout.layout_timming_pop_up,null)
            window.contentView=view
//            val imageView=view.findViewById<CardView>(R.id.card_timming)
            val crossbtn=view.findViewById<Button>(R.id.close_pop_up)
            crossbtn.setOnClickListener{
                window.dismiss()
            }
//            R.layout.activity_main.setOnTouchListener(this)



            window.showAtLocation(requireView() ,Gravity.CENTER,0,0)

        }



        return binding.root
    }


    private fun kichaui() {
        if(auth.currentUser?.email ==getString(R.string.kicha_email)){
            binding.gifMapManrunning.setVisibility(View.INVISIBLE);

        }
        else{
            binding.gifMapWomenrunning.setVisibility(View.INVISIBLE);



        }
    }
    private fun starServiceFunc(user_profession: String?){
        mLocationService = LocationService()
        mServiceIntent = Intent(getActivity(), mLocationService.javaClass)


        if(user_profession=="Bus Driver"){
            binding.btnMap4user.setVisibility(View.INVISIBLE);
            binding.btnToMap.setVisibility(View.INVISIBLE);
            binding.btnstop.setVisibility(View.VISIBLE);
        }
        else{
            binding.btnToMap.setVisibility(View.INVISIBLE);
            binding.btnstop.setVisibility(View.INVISIBLE);
        }

        if (!Util.isMyServiceRunning(mLocationService.javaClass, requireActivity())) {
            context?.startService(mServiceIntent)
            Log.i("Baground","service started in startservicfn")

            Toast.makeText(requireActivity(), getString(R.string.service_start_successfully), Toast.LENGTH_SHORT).show()
        } else {
            Log.i("Baground","service already running in startservicfn")

            Toast.makeText(requireActivity(), getString(R.string.service_already_running), Toast.LENGTH_SHORT).show()
        }
    }
    private fun stopServiceFunc(user_profession: String?){
        mLocationService = LocationService()
        mServiceIntent = Intent(getActivity(), mLocationService.javaClass)

        if(user_profession=="Bus Driver"){
            binding.btnMap4user.setVisibility(View.INVISIBLE);
            binding.btnToMap.setVisibility(View.VISIBLE);
            binding.btnstop.setVisibility(View.INVISIBLE);
        }
        else{
            binding.btnToMap.setVisibility(View.INVISIBLE);
            binding.btnstop.setVisibility(View.INVISIBLE);
        }
        if (Util.isMyServiceRunning(mLocationService.javaClass, requireActivity())) {
            context?.stopService(mServiceIntent)
            Toast.makeText(requireActivity(), "Service stopped!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireActivity(), "Service is already stopped!!", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroy() {
        /*  if (::mServiceIntent.isInitialized) {
              stopService(mServiceIntent)
          }*/
        super.onDestroy()
    }
    private fun requestBackgroundLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), MY_BACKGROUND_LOCATION_REQUEST)
    }
    private fun requestFineLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,), MY_FINE_LOCATION_REQUEST)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Toast.makeText(requireActivity(), requestCode.toString(), Toast.LENGTH_LONG).show()
        when (requestCode) {
            MY_FINE_LOCATION_REQUEST -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                        requestBackgroundLocationPermission()
                    }

                } else {
                    Toast.makeText(requireActivity(), "ACCESS_FINE_LOCATION permission denied", Toast.LENGTH_LONG).show()
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", requireActivity().packageName, null),),)
                    }
                }
                return
            }
            MY_BACKGROUND_LOCATION_REQUEST -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "Background location Permission Granted", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(requireActivity(), "Background location permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        return preferences.getString(key, null)
    }

    companion object {

        private const val MY_FINE_LOCATION_REQUEST = 99
        private const val MY_BACKGROUND_LOCATION_REQUEST = 100
    }



}