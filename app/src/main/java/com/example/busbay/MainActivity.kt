package com.example.busbay

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.busbay.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Firebase auth
        auth= Firebase.auth


        //Action Bar setup
        setupActionBar()




        val navigationView = binding.bottomNavigation //bottom_navigation

        val homeFragment= HomeFragment()
        val mapFragment= MapFragment()
        val dashboardFragment= DashboardFragment()
        val pollFragment= PollFragment()

        setCurrentFragment(homeFragment)

        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    setCurrentFragment(homeFragment)
                    true
                }
                R.id.nav_dashboard -> {
                    setCurrentFragment(dashboardFragment)
                    true
                }
                R.id.nav_map ->{
                    setCurrentFragment(mapFragment)
                    true
                }
                R.id.nav_poll ->{
                    setCurrentFragment(pollFragment)
                    true
                }
                else-> {
                    false
                }
            }
        }


    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
//            add(R.id.flFragment, fragment)
//            commit()
//            if (fragment == null) return
            val fm = supportFragmentManager
            val tr = fm.beginTransaction()
            tr.replace(R.id.body_container, fragment)
            tr.commitAllowingStateLoss()

        }
    private fun setupActionBar() {
        //Setting the name and color in action bar
        setContentView(binding.root)
        val fullName = auth.currentUser?.displayName.toString()
        val words = fullName.split("\\s".toRegex()).toTypedArray()

        ////////////////////KICHA
        if(auth.currentUser?.email == getString(R.string.kicha_email)){
            setTitle("Hey, Kicha💛" )
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2C3DB")))
        }
        else{
            setTitle("Hey, "+words[0] )
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#7dd3f5")))

        }

    }
}