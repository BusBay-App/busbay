package com.example.busbay

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.busbay.databinding.ActivityInfoDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.type.Date
import java.util.*


class InfoDetails : AppCompatActivity() {

    private lateinit var  binding: ActivityInfoDetailsBinding
    private lateinit var auth:FirebaseAuth

//    override fun onStart() {
//        super.onStart()
//        updateUi(auth.currentUser)
//    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInfoDetailsBinding.inflate(layoutInflater)

        //Firebase auth
        auth= Firebase.auth

        //Setting Visbility of text view
        binding.roomDetailsEdittext.setVisibility(View.INVISIBLE);
        binding.studentRollNumber.setVisibility(View.INVISIBLE);
        binding.yearPassoutEdittext.setVisibility(View.INVISIBLE);
        binding.branchEdittext.setVisibility(View.INVISIBLE);

        binding.driverPassword.setVisibility(View.INVISIBLE);


        //Setting the name and color in action bar
        setContentView(binding.root)
        val fullName = auth.currentUser?.displayName.toString()
        val words = fullName.split("\\s".toRegex()).toTypedArray()
        ////////////////////KICHA
        if(auth.currentUser?.email =="krishnaveni.unnikrishnan.20063@iitgoa.ac.in"){
            setTitle("Hey, Beautiful💛" )
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#146775")))
        }
        else{
            setTitle("Hey, "+words[0] )
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#146775")))

        }




        ////// Birthday date
        val calander= Calendar.getInstance()
        val year =calander.get(Calendar.YEAR)
        val month =calander.get(Calendar.MONTH)
        val day=calander.get(Calendar.DAY_OF_MONTH)
        binding.birthdate.setOnClickListener{
//            Toast.makeText(this, "clansercalled", Toast.LENGTH_SHORT).show()
            val dmy= DatePickerDialog(  this, DatePickerDialog.OnDateSetListener{view,mYear,mMonth,mDay->
                val bday= mDay.toString()+"/"+mMonth.toString()+"/"+mYear.toString()
                binding.birthdate.text=bday
            },year,month,day)
            dmy.show()

        }

        // setting up the dropdown menu
        val professionType=resources.getStringArray(R.array.Profession)
        val arrayAdapter= ArrayAdapter(this,R.layout.dropdown_item,professionType)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        Toast.makeText(this, binding.autoCompleteTextView.text.toString(), Toast.LENGTH_SHORT).show()

        binding.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            // You can get the label or item that the user clicked:
            val value = arrayAdapter.getItem(position) ?: ""

            when(value){
                "Student"-> {
                    binding.driverPassword.setVisibility(View.INVISIBLE);

//                    Toast.makeText(this, value, Toast.LENGTH_LONG).show()
                    binding.roomDetailsEdittext.setVisibility(View.VISIBLE);
                    binding.studentRollNumber.setVisibility(View.VISIBLE);
                    binding.yearPassoutEdittext.setVisibility(View.VISIBLE);
                    binding.branchEdittext.setVisibility(View.VISIBLE);



                };
                "Teacher"->{
//                    Toast.makeText(this, value, Toast.LENGTH_LONG).show()
                    binding.roomDetailsEdittext.setVisibility(View.INVISIBLE);
                    binding.studentRollNumber.setVisibility(View.INVISIBLE);
                    binding.yearPassoutEdittext.setVisibility(View.INVISIBLE);
                    binding.branchEdittext.setVisibility(View.INVISIBLE);

                    binding.driverPassword.setVisibility(View.INVISIBLE);};
                "Bus Driver"->{
                    binding.roomDetailsEdittext.setVisibility(View.INVISIBLE);
                    binding.studentRollNumber.setVisibility(View.INVISIBLE);
                    binding.yearPassoutEdittext.setVisibility(View.INVISIBLE);
                    binding.branchEdittext.setVisibility(View.INVISIBLE);

//                    Toast.makeText(this, value, Toast.LENGTH_LONG).show()
                    binding.driverPassword.setVisibility(View.VISIBLE);
                };
                "Others"->{
//                    Toast.makeText(this, value, Toast.LENGTH_LONG).show()
                    binding.roomDetailsEdittext.setVisibility(View.INVISIBLE);
                    binding.studentRollNumber.setVisibility(View.INVISIBLE);
                    binding.yearPassoutEdittext.setVisibility(View.INVISIBLE);
                    binding.branchEdittext.setVisibility(View.INVISIBLE);

                    binding.driverPassword.setVisibility(View.INVISIBLE);
                };
            }

        }

        binding.btnSubmitDetails.setOnClickListener {
            val phno=binding.phoneNumber.text.toString()
            val dob=binding.birthdate.text.toString()
            //student
            val branch=binding.branchEdittext.text.toString()
            val yearpass=binding.yearPassoutEdittext.text.toString()
            val numberroll=binding.studentRollNumber.text.toString()
            val detailsroom=binding.roomDetailsEdittext.text.toString()

            //driver
            val passwordDriver= binding.driverPassword.text.toString()

            when(binding.autoCompleteTextView.text.toString()){
                "Student"->{
                    if((phno!="") && (dob!="") && (branch!="") && (yearpass!="")
                        && (numberroll!="") && (detailsroom!="")   ){
                        Toast.makeText(this, binding.autoCompleteTextView.text.toString(), Toast.LENGTH_SHORT).show()

                        updateUi(auth.currentUser)
                    }
                    else{
                        Toast.makeText(this, "Kindly Fill all the Details!!", Toast.LENGTH_SHORT).show()
                    }
                };
                "Teacher"->{
                    if((phno!="") and (dob!="")   ){
                        updateUi(auth.currentUser)
                    }
                    else{
                        Toast.makeText(this, "Kindly Fill all the Details!!", Toast.LENGTH_SHORT).show()
                    }
                };
                "Bus Driver"->{
                    if((phno!="") and (dob!="") and (passwordDriver!="")   ){
                        if(binding.driverPassword.text.toString()=="123456") {
                            updateUi(auth.currentUser)
                        }
                        else{
                            Toast.makeText(this, "Enter correct Password", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this, "Kindly Fill all the Details!!", Toast.LENGTH_SHORT).show()
                    }
                };
                "Others"->{
                    if((phno!="") and (dob!="")   ){
                        updateUi(auth.currentUser)
                    }
                    else{
                        Toast.makeText(this, "Kindly Fill all the Details!!", Toast.LENGTH_SHORT).show()
                    }
                };
                else->{
                    Toast.makeText(this, "Choose Your Profession", Toast.LENGTH_SHORT).show()
                }
            }
            

        }



    }
    private fun updateUi(currentUser: FirebaseUser?) {
        if(currentUser==null){
            return
        }
        startActivity(Intent(this,MainActivity::class.java))  /////////////////////////////////////////////
        finish()

    }
}