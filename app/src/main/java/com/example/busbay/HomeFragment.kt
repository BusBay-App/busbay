package com.example.busbay

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.busbay.databinding.FragmentHomeBinding
import com.example.busbay.databinding.FragmentMapBinding
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {

//    private lateinit var mDrawerToggle: ActionBarDrawerToggle


//    _____________________________
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    lateinit var imgProfilePic: ShapeableImageView //

    lateinit var btnComplain: Button //
//_________________________________________________________
    lateinit var toggle: ActionBarDrawerToggle
// ___________________________________________________

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding= FragmentHomeBinding.inflate(inflater,container,false)
        //Firebase auth
        auth= Firebase.auth
        //intialising  button
        btnComplain=binding.btnToComplain

        //different UI

        //setting profile pic

//        mDrawerToggle = ActionBarDrawerToggle(requireActivity(),binding.drawerLayout,binding.toolbar, R.string.open, R.string.close)
//        mDrawerToggle!!.syncState()
//        binding.drawerLayout!!.closeDrawer(GravityCompat.START)
        setProfilepic()

        btnComplain.setOnClickListener {
            val intent= Intent(getActivity(),ComplainActivity::class.java)///
            startActivity(intent)
        }
//        binding.btnNavDrawer.setOnClickListener {
//            Toast.makeText(requireActivity(), "touched", Toast.LENGTH_SHORT).show()
//
//        }




        return binding.root
    }

    private fun setProfilepic() {
        imgProfilePic=binding.idProfliePic
        val profileimageURL=auth.currentUser?.photoUrl.toString()



        Glide.with(this).load(profileimageURL).into(imgProfilePic);
    }




}