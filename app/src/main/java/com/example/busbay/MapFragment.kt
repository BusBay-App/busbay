package com.example.busbay

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busbay.databinding.FragmentMapBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMapBinding.inflate(inflater,container,false)
        //Firebase auth
        auth= Firebase.auth

        //different UI
        kichaui()

        binding.btnToMap.setOnClickListener {
            startActivity(Intent(getActivity(),MapActivity::class.java))  /////////////////////////////////////////////

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



}