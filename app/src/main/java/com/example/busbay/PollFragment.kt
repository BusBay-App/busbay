package com.example.busbay

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.busbay.databinding.FragmentMapBinding
import com.example.busbay.databinding.FragmentPollBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class PollFragment : Fragment() {
    private lateinit var binding: FragmentPollBinding
    private lateinit var auth: FirebaseAuth
    lateinit var addPoll: FloatingActionButton //
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentPollBinding.inflate(inflater,container,false)
        //Firebase auth
        auth= Firebase.auth//different UI


        addPoll=binding.floatingActionButtonPoll

        addPoll.setOnClickListener { GotoAddPollactivity()        }

        return binding.root
    }

    private fun GotoAddPollactivity() {
        val intent= Intent(getActivity(),AddPollActivity::class.java) ///
        startActivity(intent)
    }


}