package com.example.busbay

import android.app.DownloadManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.busbay.databinding.ActivityAddComplainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AddComplain : AppCompatActivity() {

    private lateinit var binding: ActivityAddComplainBinding
    private lateinit var auth: FirebaseAuth
    lateinit var checkbox_maintenance: CheckBox //
    lateinit var checkbox_mess: CheckBox //
    lateinit var checkbox_others: CheckBox //

    lateinit var text_subject: EditText //
    lateinit var text_content: EditText //
    lateinit var btnSubmit: Button //
    private  lateinit var database :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddComplainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= Firebase.auth
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        //intialising
        checkbox_maintenance=binding.checkBoxMaintenance
        checkbox_mess=binding.checkBoxMess
        checkbox_others=binding.checkBoxOthers
        text_subject=binding.edittextComplainSubject
        text_content=binding.editTextContentcomplain
        btnSubmit=binding.btnSubmitComplain
        database=  Firebase.database.getReference("Complaian")

//        ( rollnumber/email_id_start, type, room_details, email_id, phone_no, subject, issue)



        //onClick listeners
        //checboxes
        checkbox_mess.setOnClickListener { switchCheckBox(checkbox_mess)}
        checkbox_maintenance.setOnClickListener { switchCheckBox(checkbox_maintenance)}
        checkbox_others.setOnClickListener { switchCheckBox(checkbox_others)}
        //Submit button
        btnSubmit.setOnClickListener { check_submit()}






    }

    //Here i have tried doing with 2 dataset 1)Firebase,,,2) Google sheet...Sheet being more scalabe we going with it
    private fun check_submit() {

        //curret time
        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now()
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")
        val formatted = current.format(formatter)
        //submit all details when content and subject is filled
        val roll_no=getDefaults("Roll_No")
        val room_details=getDefaults("Room_Details")
        val ph_no=getDefaults("Phone_No")
        val email= auth.currentUser?.email.toString()
        val subject =text_subject.text.toString()
        val content=text_content.text.toString()
        val type=getCheeckboxDetails()
        val emaild_start=formatted+" "+subject ///unique id

        val complainUser=ComplainUser(roll_no,type,room_details,email,ph_no,subject,content)




        /////

        if((subject!="") && (content!="") && (checboxIsChecked()) ){
            //GOOGLE SHEET METHOD
            uploadOnGoogleSheet(formatted,roll_no,room_details,ph_no,email,subject,content,type)
            resetAllView()

            //FIREBASE METHOD
//            database.child(emaild_start).setValue(complainUser).addOnSuccessListener {
////                Toast.makeText(this, "Success added", Toast.LENGTH_SHORT).show()
//                resetAllView()
//
//            }.addOnFailureListener {
//                Toast.makeText(this, "Failure Not added", Toast.LENGTH_SHORT).show()
//
//
//            }

        }
        else{
            if((subject!="") &&(checboxIsChecked()) ){
                Toast.makeText(this, "Please Enter the Content", Toast.LENGTH_SHORT).show()
            }
            else if(!checboxIsChecked()){
                Toast.makeText(this, "Please Check the Checkbox", Toast.LENGTH_SHORT).show()

            }
            else{
                Toast.makeText(this, "Please Enter the Subject", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadOnGoogleSheet(
        formatted: String?,
        rollNo: String?,
        roomDetails: String?,
        phNo: String?,
        email: String,
        subject: String,
        content: String,
        type: String
    ) {

    /////Google Sheet//   https://script.google.com/home/projects/1_qzde8-SkrrjrNbY9eZqu0uNNNFiJ0tjN8jN3HJ6DLUZo2m0MfRE3fEq/edit
    val url=BuildConfig.Complain_API_POST
        val stringRequest=object:StringRequest(Request.Method.POST,url,
        Response.Listener {
            Toast.makeText(this, "Succes added", Toast.LENGTH_SHORT).show()
        },
        Response.ErrorListener {
            Toast.makeText(this, "volley erroe", Toast.LENGTH_SHORT).show()
            Log.i("Volley",it.toString())
        }){
        override fun getParams(): MutableMap<String, String>? {
            //        ( rollnumber/email_id_start, type, room_details, email_id, phone_no, subject, issue)

            val params=HashMap<String,String> ()
            params["Date_Time"]=formatted.toString()
            params["Roll_No"]=rollNo.toString()
            params["Email_Id"]=email
            params["Type"]=type
            params["Room_Details"]=roomDetails.toString()
            params["Phone_No"]=phNo.toString()
            params["Subject"]=subject
            params["Issue"]=content
            return params
        }
    }
    val queue=Volley.newRequestQueue(this)
    queue.add(stringRequest)

    }



    private fun checboxIsChecked(): Boolean {
        if((checkbox_others.isChecked)|| (checkbox_mess.isChecked) ||(checkbox_maintenance.isChecked)){
            return true
        }
        return false
    }

    private fun resetAllView() {
        text_content.setText("")
        text_subject.setText("")
        checkbox_others.isChecked=false
        checkbox_mess.isChecked=false
        checkbox_maintenance.isChecked=false
    }


    private fun getCheeckboxDetails(): String {
            if(checkbox_mess.isChecked){
                return "Mess"
            }
            else if(checkbox_maintenance.isChecked){
                return "Maintenance"
            }
            return "Others"
    }

    private fun switchCheckBox(checked: CheckBox) {
        when(checked){
            checkbox_mess->{
                checkbox_mess.isChecked=true
                checkbox_others.isChecked=false
                checkbox_maintenance.isChecked=false
            };
            checkbox_maintenance->{
                checkbox_maintenance.isChecked=true
                checkbox_others.isChecked=false
                checkbox_mess.isChecked=false


            };
            checkbox_others->{
                checkbox_others.isChecked=true
                checkbox_mess.isChecked=false
                checkbox_maintenance.isChecked=false
            }
        }


    }

    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        return preferences.getString(key, null)
    }
}