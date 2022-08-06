package com.example.busbay

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.busbay.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class MainActivity : AppCompatActivity() {

//    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var  binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Firebase auth
        auth= Firebase.auth


        //Action Bar setup
        setupActionBar()

//        throw RuntimeException("Test Crash") // Force a crash

        val navigationView = binding.bottomNavigation //bottom_navigation
        val btntopNav=binding.btnNavDrawer

        val homeFragment= HomeFragment()
        val mapFragment= MapFragment()
        val dashboardFragment= DashboardFragment()
        val pollFragment= PollFragment()

        setCurrentFragment(homeFragment)

        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    setCurrentFragment(homeFragment)
                    btntopNav.setVisibility(View.VISIBLE);

                    true
                }
                R.id.nav_dashboard -> {
                    setCurrentFragment(dashboardFragment)
                    btntopNav.setVisibility(View.INVISIBLE);

                    true
                }
                R.id.nav_map ->{
                    setCurrentFragment(mapFragment)
                    btntopNav.setVisibility(View.INVISIBLE);

                    true
                }
                R.id.nav_poll ->{
                    btntopNav.setVisibility(View.INVISIBLE);

                    if(getDefaults("Profession")=="Student" || getDefaults("Profession")=="Teacher" )
                    {
                        setCurrentFragment(pollFragment)
                        true                        
                    }
                    else {
                        Toast.makeText(this, "Only Students are allowed to view Poll", Toast.LENGTH_SHORT).show()
                        false
                    }

                    
                }
                else-> {
                    false
                }
            }
        }
//        mDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar, R.string.open, R.string.close)
//        mDrawerToggle!!.syncState()
//        binding.drawerLayout!!.closeDrawer(GravityCompat.START)


        binding.btnNavDrawer.setOnClickListener {

            Log.i("pop up menu","Tried1")
            val popupmenu = PopupMenu(this,it)
            // Toast.makeText(requireActivity(), "touched", Toast.LENGTH_SHORT).show()
            Log.i("pop up menu","Tried")
            popupmenu.setOnMenuItemClickListener {item->
                Log.i("pop up menu","Success")
//                if(item.itemId == R.id.fragmentAbout){
//                    intent = new Intent(this, )
//                }
//

                when(item.itemId){
                    R.id.fragmentAbout-> {
//                        var intent = Intent(view?.context, AboutFragment::class.java)
//                        Log.i("intent", intent.toString())
//                        startActivity(intent)
                        btntopNav.setVisibility(View.VISIBLE);

                        setCurrentFragment(AboutFragment())

//                        Toast.makeText(requireActivity(), "entered", Toast.LENGTH_SHORT).show()
                        true}
                    R.id.fragmentContact-> {
                        btntopNav.setVisibility(View.VISIBLE);

                        setCurrentFragment(ContactsFragment())
//                        findNavController().navigate(HomeFragmentDirections.actionFragmentHomeToFragmentContact())
                        true}
                    else-> false
                }
            }
            popupmenu.inflate(R.menu.drawer_menu)
            popupmenu.show()
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
        if(auth.currentUser?.email == BuildConfig.kicha_email){
            setTitle(BuildConfig.kicha_string)
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#D2C3DB")))
        }
        else{
            setTitle("Hey, "+words[0] )
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#7dd3f5")))

        }

    }
    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        return preferences.getString(key, null)
    }
}