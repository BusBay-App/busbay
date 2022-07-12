package com.example.busbay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.busbay.databinding.ActivityComplainBinding
import com.example.busbay.databinding.ActivityMapsViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ComplainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComplainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComplainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //user auth
        auth= Firebase.auth

        binding.floatingActionButton.setOnClickListener{
            startActivity(Intent(this,AddComplain::class.java))
        }


    }
}