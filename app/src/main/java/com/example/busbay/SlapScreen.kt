package com.example.busbay

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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


        //Action Bar setup
        setupActionBar()


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

    private fun setupActionBar() {
        //Setting the name and color in action bar
        setContentView(binding.root)
        val fullName = auth.currentUser?.displayName.toString()
        val words = fullName.split("\\s".toRegex()).toTypedArray()

        ////////////////////KICHA
        if(auth.currentUser?.email ==getString(R.string.kicha_email)){
            setTitle("Hey, Beautiful💛" )
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2C3DB")))
        }
        else{
            setTitle("Hey, "+words[0] )
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#F9E79F")))

        }

    }


}