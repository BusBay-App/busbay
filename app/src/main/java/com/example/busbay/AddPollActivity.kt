package com.example.busbay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.busbay.databinding.ActivityAddPollBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddPollActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPollBinding
    private lateinit var auth: FirebaseAuth
    lateinit var chipsBtech: Chip //
    lateinit var chipgorup1: ChipGroup //
    


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //user auth
        auth= Firebase.auth
        chipgorup1=binding.chipgroup1
        
        chipsBtech=binding.chipBtech

        
        if(chipsBtech.callOnClick()){
            Toast.makeText(this, "btech is cheked", Toast.LENGTH_SHORT).show()
        }

//        chipsBtech.setOnClickListener {
//            if(!chipsBtech.isChecked){
//                chipsBtech.isChecked=true
//            }
//        }

    }
}