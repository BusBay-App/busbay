package com.example.busbay

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.busbay.databinding.ActivityInfoDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class InfoDetails : AppCompatActivity() {

    private lateinit var  binding: ActivityInfoDetailsBinding
    private lateinit var auth:FirebaseAuth

//    override fun onStart() {
//        super.onStart()
//
//    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    binding= ActivityInfoDetailsBinding.inflate(layoutInflater)

    //Firebase auth
    auth= Firebase.auth


//
//    //check if already exits
//    val mFireStore = FirebaseFirestore.getInstance()
//    val docref = mFireStore.collection("users").document(auth.currentUser?.email.toString())
//
//    docref.get().addOnCompleteListener { task ->
//        if (task.isSuccessful) {
//            Toast.makeText(applicationContext, "Exists", Toast.LENGTH_SHORT).show()
//
//            startActivity(Intent(applicationContext, MainActivity::class.java))
//            finish()
//
//        }
//    }
//
//
//    Toast.makeText(this, "not runngng", Toast.LENGTH_SHORT).show()




        //Setting Visbility of text view
        binding.roomDetailsEdittext.setVisibility(View.INVISIBLE);
        binding.studentRollNumber.setVisibility(View.INVISIBLE);
        binding.yearPassoutEdittext.setVisibility(View.INVISIBLE);
        binding.branchEdittext.setVisibility(View.INVISIBLE);
        binding.textfeildbranchdrop.setVisibility(View.INVISIBLE);

        binding.driverPassword.setVisibility(View.INVISIBLE);




        //Setting the name and color in action bar
        setContentView(binding.root)
        val fullName = auth.currentUser?.displayName.toString()
        val words = fullName.split("\\s".toRegex()).toTypedArray()
        ////////////////////KICHA
        if(auth.currentUser?.email =="R.string.kicha_email"){
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

    // setting up the Branchdropdown menu
    val branchType=resources.getStringArray(R.array.Branch)
    val arrayAdapter1= ArrayAdapter(this,R.layout.dropdown_item,branchType)
    binding.branchEdittext.setAdapter(arrayAdapter1)

//        Toast.makeText(this, binding.autoCompleteTextView.text.toString(), Toast.LENGTH_SHORT).show()

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
                    binding.textfeildbranchdrop.setVisibility(View.VISIBLE);



                };
                "Teacher"->{
//                    Toast.makeText(this, value, Toast.LENGTH_LONG).show()
                    binding.roomDetailsEdittext.setVisibility(View.INVISIBLE);
                    binding.studentRollNumber.setVisibility(View.INVISIBLE);
                    binding.yearPassoutEdittext.setVisibility(View.INVISIBLE);
                    binding.branchEdittext.setVisibility(View.VISIBLE);
                    binding.textfeildbranchdrop.setVisibility(View.VISIBLE);


                    binding.driverPassword.setVisibility(View.INVISIBLE);};
                "Bus Driver"->{
                    binding.roomDetailsEdittext.setVisibility(View.INVISIBLE);
                    binding.studentRollNumber.setVisibility(View.INVISIBLE);
                    binding.yearPassoutEdittext.setVisibility(View.INVISIBLE);
                    binding.branchEdittext.setVisibility(View.INVISIBLE);
                    binding.textfeildbranchdrop.setVisibility(View.INVISIBLE);


//                    Toast.makeText(this, value, Toast.LENGTH_LONG).show()
                    binding.driverPassword.setVisibility(View.VISIBLE);
                };
                "Others"->{
//                    Toast.makeText(this, value, Toast.LENGTH_LONG).show()
                    binding.roomDetailsEdittext.setVisibility(View.INVISIBLE);
                    binding.studentRollNumber.setVisibility(View.INVISIBLE);
                    binding.yearPassoutEdittext.setVisibility(View.INVISIBLE);
                    binding.branchEdittext.setVisibility(View.INVISIBLE);
                    binding.textfeildbranchdrop.setVisibility(View.INVISIBLE);


                    binding.driverPassword.setVisibility(View.INVISIBLE);
                };
            }

        }

        binding.btnSubmitDetails.setOnClickListener {
            val professionn=binding.autoCompleteTextView.text.toString()
            val phno=binding.phoneNumber.text.toString()
            val dob=binding.birthdate.text.toString()
            //student
            val branch=binding.branchEdittext.text.toString()
            val yearpass=binding.yearPassoutEdittext.text.toString()
            val numberroll=binding.studentRollNumber.text.toString()
            val detailsroom=binding.roomDetailsEdittext.text.toString()

//            Toast.makeText(this, branch, Toast.LENGTH_SHORT).show()

            //driver
            val passwordDriver= binding.driverPassword.text.toString()

            when(professionn){
                "Student"->{
                    if((phno!="") && (dob!="") && (branch!="") && (yearpass!="")
                        && (numberroll!="") && (detailsroom!="")   ){
                        saveProfessionDatalocally("Student")
                        saveRollNoDatalocally(numberroll)
                        savePhoneNumberDatalocally(phno)
                        saveRoomDetailsDatalocally(detailsroom)
                        savePassOutYearDatalocally(yearpass)
                        saveBranchDatalocally(branch)

                        Toast.makeText(this, binding.autoCompleteTextView.text.toString(), Toast.LENGTH_SHORT).show()
                        saveFirestore(professionn,phno,dob,branch,yearpass,numberroll,detailsroom,passwordDriver)
                        updateUi(auth.currentUser)
                    }
                    else{
                        Toast.makeText(this, "Kindly Fill all the Details!!", Toast.LENGTH_SHORT).show()
                    }
                };
                "Teacher"->{
                    if((phno!="") and (dob!="") and (branch!="")  ){

                        saveProfessionDatalocally("Teacher")
                        saveRollNoDatalocally(numberroll)
                        savePhoneNumberDatalocally(phno)
                        saveRoomDetailsDatalocally(detailsroom)
                        savePassOutYearDatalocally(yearpass)
                        saveBranchDatalocally(branch)
                        updateUi(auth.currentUser)

                        saveFirestore(professionn,phno,dob,branch,yearpass,numberroll,detailsroom,passwordDriver)

                    }
                    else{
                        Toast.makeText(this, "Kindly Fill all the Details!!", Toast.LENGTH_SHORT).show()
                    }
                };
                "Bus Driver"->{
                    if((phno!="") and (dob!="") and (passwordDriver!="")   ){
                        if(binding.driverPassword.text.toString()==BuildConfig.Bus_Pass) {
                            saveProfessionDatalocally("Bus Driver")
                            saveRollNoDatalocally(numberroll)
                            savePhoneNumberDatalocally(phno)
                            saveRoomDetailsDatalocally(detailsroom)
                            savePassOutYearDatalocally(yearpass)
                            saveBranchDatalocally(branch)

                            updateUi(auth.currentUser)
                            saveFirestore(professionn,phno,dob,branch,yearpass,numberroll,detailsroom,passwordDriver)

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
                        saveProfessionDatalocally("Others")
                        saveRollNoDatalocally(numberroll)
                        savePhoneNumberDatalocally(phno)
                        saveRoomDetailsDatalocally(detailsroom)
                        savePassOutYearDatalocally(yearpass)
                        saveBranchDatalocally(branch)
                        saveFirestore(professionn,phno,dob,branch,yearpass,numberroll,detailsroom,passwordDriver)

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

    private fun saveProfessionDatalocally(profession: String) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("Profession", profession)
        editor.apply()
    }
    private fun saveRollNoDatalocally(roll_no: String) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("Roll_No", roll_no)
        editor.apply()
    }
    private fun saveRoomDetailsDatalocally(room_deatils: String) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("Room_Details", room_deatils)
        editor.apply()
    }
    private fun savePhoneNumberDatalocally(phoneNo: String) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("Phone_No", phoneNo)
        editor.apply()
    }
    private fun savePassOutYearDatalocally(yearpass: String) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("PassOutYear", yearpass)
        editor.apply()
    }
    private fun saveBranchDatalocally(branch: String) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("Branch", branch)
        editor.apply()
    }




    private fun saveFirestore(

        professionn: String ,
        phno: String,
        dob: String,
        branch: String =" ",
        yearpass: String =" ",
        numberroll: String =" ",
        detailsroom: String =" ",
        passwordDriver: String =" "
    ) {
        val db =Firebase.firestore
//        val db =FirebaseFirestore.getInstance()
        val user: MutableMap<String,Any> = hashMapOf()

        user["block_room_no"]=detailsroom
        user["branch"]=branch
        user["dob"]=dob
        user["email_id"]= auth.currentUser?.email.toString()
        user["passout_year"]=yearpass
        user["password"]=passwordDriver
        user["phone_no"]=phno
        user["profession"]=professionn
        user["roll_num"]=numberroll


        db.collection("users").document(auth.currentUser?.email.toString())
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {e ->
                Log.i("database", "Error adding document", e)
                Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
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