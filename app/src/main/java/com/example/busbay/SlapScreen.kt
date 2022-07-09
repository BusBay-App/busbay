package com.example.busbay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.busbay.databinding.ActivityInfoDetailsBinding
import com.example.busbay.databinding.ActivitySlapScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SlapScreen : AppCompatActivity() {
    private lateinit var  binding: ActivitySlapScreenBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySlapScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Firebase auth
        auth= Firebase.auth

        //check if already exits
        val mFireStore = FirebaseFirestore.getInstance()
        val docref = mFireStore.collection("users").document(auth.currentUser?.email.toString())

        docref.get().addOnCompleteListener { task ->

            if ((task.isSuccessful) &&  ( task.result.exists())) {
//                Toast.makeText(applicationContext, "Exists"+task.result, Toast.LENGTH_SHORT).show()

                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()

            }
            else{
                startActivity(Intent(applicationContext, InfoDetails::class.java))

            }
        }


//        Toast.makeText(this, "not runngng", Toast.LENGTH_SHORT).show()
    }
}