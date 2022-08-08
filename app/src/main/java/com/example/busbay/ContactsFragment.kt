package com.example.busbay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busbay.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {


    private lateinit var binding : FragmentContactsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentContactsBinding.inflate(inflater,container,false)



        binding.c1.setOnClickListener{
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "+919284732433")
            startActivity(dialIntent)
        }
        binding.c2.setOnClickListener{
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "+917083355196")
            startActivity(dialIntent)
        }
        binding.c3.setOnClickListener{
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "+917378599398")
            startActivity(dialIntent)
        }
        binding.c4.setOnClickListener{
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "+919356498590")
            startActivity(dialIntent)
        }
        binding.c5.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "+919370508408")
            startActivity(dialIntent)
        }
//        fun call(view: View) {
//            val dialIntent = Intent(Intent.ACTION_DIAL)
//            dialIntent.data = Uri.parse("tel:" + "8344814819")
//            startActivity(dialIntent)
//        }
        return binding.root
    }

}