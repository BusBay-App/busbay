package com.example.busbay

import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.busbay.databinding.ActivityAddComplainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


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






//        val currentUser= auth.currentUser?.uid.toString()
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("Complain")
//        myRef.child(currentUser).setValue("aaa").addOnSuccessListener {
//            Log.i("Baground","latitude and long updated")
//
//        }.addOnFailureListener{
//            Log.i("Baground","latitude and long update failed")
//
//        }
    }

    private fun check_submit() {
        //submit all details when content and subject is filled
        val roll_no=getDefaults("Roll_No")
        val room_details=getDefaults("Room_Details")
        val ph_no=getDefaults("Phone_No")
        val email= auth.currentUser?.email.toString()
        val subject =text_subject.text.toString()
        val content=text_content.text.toString()
        val type=getCheeckboxDetails()
        val emaild_start=ph_no+subject ///unique id

        val complainUser=ComplainUser(roll_no,type,room_details,email,ph_no,subject,content)




        if((subject!="") && (content!="") && (checboxIsChecked()) ){
            database.child(emaild_start).setValue(complainUser).addOnSuccessListener {
//                Toast.makeText(this, "Success added", Toast.LENGTH_SHORT).show()
                resetAllView()

            }.addOnFailureListener {
                Toast.makeText(this, "Failure Not added", Toast.LENGTH_SHORT).show()


            }
            Toast.makeText(this, roll_no+type
                    +room_details+ email+ph_no+subject+content, Toast.LENGTH_SHORT).show()
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

    private fun getEmaillStart(email: String): String {
//        var s=""
        val extensionRemoved: String = email.split("\\@").get(0)
        Toast.makeText(this, extensionRemoved, Toast.LENGTH_SHORT).show()
        return extensionRemoved
//        for (i in 0..email.length-1) {
//            if(email[i]=='a'){
//                break
//            }
//            else{
//                s.plus(email[i])
//            }
//
//        }
//        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()
//        return s
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