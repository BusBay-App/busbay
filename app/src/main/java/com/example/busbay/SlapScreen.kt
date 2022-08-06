package com.example.busbay

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.busbay.databinding.ActivityInfoDetailsBinding
import com.example.busbay.databinding.ActivitySlapScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.schedule

class SlapScreen : AppCompatActivity() {
    private lateinit var  binding: ActivitySlapScreenBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySlapScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        Toast.makeText(this, user_profession, Toast.LENGTH_SHORT).show()

        //Firebase auth
        auth= Firebase.auth
        setupActionBar()

        Timer().schedule(1500){

            //calling a function
            changeActivity()
        }






        //check if already exits
//        val mFireStore = FirebaseFirestore.getInstance()
//        val docref = mFireStore.collection("users").document(auth.currentUser?.email.toString())

//        docref.get().addOnCompleteListener { task ->

//            if ((task.isSuccessful) &&  ( task.result.exists()))

//        }


//        Toast.makeText(this, "not runngng", Toast.LENGTH_SHORT).show()
    }

    private fun changeActivity() {
        val  user_profession=getDefaults("Profession")


        if((user_profession=="Bus Driver") || (user_profession=="Teacher") ||
            (user_profession=="Student") || (user_profession=="Others") ){
//                Toast.makeText(applicationContext, "Exists"+task.result, Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()


            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()

        }
        else{
//                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show()

            startActivity(Intent(applicationContext, InfoDetails::class.java))

        }
    }

    override fun onStart() {
        super.onStart()


    }


    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        return preferences.getString(key, null)
    }


    private fun setupActionBar() {
        //Setting the name and color in action bar
        setContentView(binding.root)
        val fullName = auth.currentUser?.displayName.toString()
        val words = fullName.split("\\s".toRegex()).toTypedArray()

        ////////////////////KICHA
        if(auth.currentUser?.email == BuildConfig.kicha_email){
            setTitle(BuildConfig.heybeutiful)
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2C3DB")))
        }
        else{
            setTitle("Hey, "+words[0] )
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#F9E79F")))

        }

    }


}