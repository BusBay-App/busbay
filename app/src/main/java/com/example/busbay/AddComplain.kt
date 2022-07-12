package com.example.busbay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.busbay.databinding.ActivityAddComplainBinding
import com.example.busbay.databinding.ActivityMapsViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class AddComplain : AppCompatActivity() {

    private lateinit var binding: ActivityAddComplainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddComplainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= Firebase.auth



//        val currentUser= auth.currentUser?.uid.toString()
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("Complain")
//        myRef.child(currentUser).setValue("aaa").addOnSuccessListener {
//            Log.i("Baground","latitude and long updated")
//
//        }.addOnFailureListener{
//            Log.i("Baground","latitude and long update failed")
//
//        }
    }
}