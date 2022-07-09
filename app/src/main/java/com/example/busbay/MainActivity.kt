package com.example.busbay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.busbay.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//    //_______________________________
//        supportFragmentManager().hide();
//        //_________________________________

        val navigationView = binding.bottomNavigation //bottom_navigation

        val homeFragment= HomeFragment()
        val mapFragment= MapFragment()
        val dashboardFragment= DashboardFragment()

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
}