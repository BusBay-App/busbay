package com.example.busbay


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import com.example.busbay.databinding.ActivityAddPollBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class AddPollActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPollBinding
    private lateinit var auth: FirebaseAuth


    lateinit var chipgorup1: ChipGroup //
    lateinit var chipgroupalltags: ChipGroup //
    lateinit var btnPublishPoll: Button //
    lateinit var btnQuestionPoll: Button //

    //popular tags
    lateinit var chipsBtech: Chip //
    lateinit var chipsMtech: Chip //
    lateinit var chipsPhd: Chip //
    lateinit var chipsProff: Chip //

    //ALl tags
    lateinit var chip1: Chip //
    lateinit var chip2: Chip //
    lateinit var chip3: Chip //
    lateinit var chip4: Chip //
    lateinit var chip5: Chip //
    lateinit var chip6: Chip //
    lateinit var chip7: Chip //
    lateinit var chip8: Chip //
    lateinit var chip9: Chip //
    lateinit var chip10: Chip //
    lateinit var chip11: Chip //
    lateinit var chip12: Chip //
    lateinit var chip13: Chip //
    lateinit var chip14: Chip //
    lateinit var chip15: Chip //
    lateinit var chip16: Chip //
    lateinit var chip17: Chip //
    lateinit var chip18: Chip //
    lateinit var chip19: Chip //
    lateinit var chip20: Chip //

    var numberOptions=1

    ///OPTIONS INtialisation

    lateinit var option1: EditText //
    lateinit var option2: EditText //
    lateinit var option3: EditText //
    lateinit var option4: EditText //
    lateinit var option5: EditText //
    lateinit var option6: EditText //
    lateinit var option7: EditText //

    var question=""
    var optn1=""
    var optn2=""
    var optn3=""
    var optn4=""
    var optn5=""
    var optn6=""
    var optn7=""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPollBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //user auth
        auth= Firebase.auth
        chipgorup1=binding.chipgroup1
        chipgroupalltags=binding.chipsGroupAlltags

        btnPublishPoll=binding.btnPublishPoll
        btnQuestionPoll=binding.btnQuestionPoll


        //Initialisation of chips
        chipsBtech=binding.chipBtech
        chipsPhd=binding.chipPhd
        chipsMtech=binding.chipMtech
        chipsProff=binding.chipProff

        chip1=binding.chip1
        chip2=binding.chip2
        chip3=binding.chip3
        chip4=binding.chip4
        chip5=binding.chip5
        chip6=binding.chip6
        chip7=binding.chip7
        chip8=binding.chip8
        chip9=binding.chip9
        chip10=binding.chip10
        chip11=binding.chip11
        chip12=binding.chip12
        chip13=binding.chip13
        chip14=binding.chip14
        chip15=binding.chip15
        chip16=binding.chip16
        chip17=binding.chip17
        chip18=binding.chip18
        chip19=binding.chip19
        chip20=binding.chip20

        //array for chips
        val chipsArray= arrayOf(chip1,chip2,chip3,chip4,chip5,chip6,chip7,chip8,chip9,chip10,chip11,chip12,chip13,chip14,chip15,chip16,chip17,chip18,chip19,chip20,chipsBtech,chipsMtech,chipsPhd,chipsProff)
        var arrayVariableSave= arrayOf(question,optn1,optn2,optn3,optn4,optn5,optn6,optn7)

        //current year
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)-2000

        //setting the text of chips

        chip1.text="CSE"+(year-4).toString()
        chip2.text="MnC"+(year-4).toString()
        chip3.text="EE"+(year-4).toString()
        chip4.text="ME"+(year-4).toString()
        chip5.text="CSE"+(year-3).toString()
        chip6.text="MnC"+(year-3).toString()
        chip7.text="EE"+(year-3).toString()
        chip8.text="ME"+(year-3).toString()
        chip9.text="CSE"+(year-2).toString()
        chip10.text="MnC"+(year-2).toString()
        chip11.text="EE"+(year-2).toString()
        chip12.text="ME"+(year-2).toString()
        chip13.text="CSE"+(year-1).toString()
        chip14.text="Mnc"+(year-1).toString()
        chip15.text="EE"+(year-1).toString()
        chip16.text="ME"+(year-1).toString()
        chip17.text="CSE"+(year).toString()
        chip18.text="MnC"+(year).toString()
        chip19.text="EE"+(year).toString()
        chip20.text="ME"+(year).toString()


//        for (i in 0 until chipgroupalltags.getChildCount()) {
//
//            if(chipgroupalltags.getChildAt(i).isChecked) {
//                //this chip is selected.....
//            }
//        }


        //////////BOTTOM SHEET

        val view: View =layoutInflater.inflate(R.layout.poll_bottom_sheet,null)
        val dialog=BottomSheetDialog(this)



        ////botom sheet intitalsiation
        var button_save:Button?= view.findViewById(R.id.btnSavePoll)!!
        var button_addPollOption:Button?= view.findViewById(R.id.btnaddAnotherOption)!!
        //question
        val questiontextview:EditText?= view.findViewById(R.id.questionPoll)!!
        //option
        val option1:EditText?=view.findViewById(R.id.editTextanswer1)!!
        val option2:EditText?=view.findViewById(R.id.editTextanswer2)!!
        val option3:EditText?=view.findViewById(R.id.editTextanswer3)!!
        val option4:EditText?=view.findViewById(R.id.editTextanswer4)!!
        val option5:EditText?=view.findViewById(R.id.editTextanswer5)!!
        val option6:EditText?=view.findViewById(R.id.editTextanswer6)!!
        val option7:EditText?=view.findViewById(R.id.editTextanswer7)!!

        val optionsArray= arrayOf(option1,option2,option3,option4,option5,option6,option7)

        btnQuestionPoll.setOnClickListener {
            questiontextview?.setText( question)
            option1?.setText(optn1 )
            option2?.setText(optn2 )
            option3?.setText(optn3 )
            option4?.setText(optn4 )
            option5?.setText(optn5 )
            option6?.setText(optn6 )
            option7?.setText(optn7 )


            dialog.setContentView(view)
            dialog.show()
        }

//        binding.driverPassword.setVisibility(View.INVISIBLE);
        button_addPollOption?.setOnClickListener {
            numberOptions++
            if(numberOptions>7){
                Toast.makeText(this, "You can add atmost 7 Option's", Toast.LENGTH_SHORT).show()
            }
            else{
                optionsArray[numberOptions-1]?.setVisibility(View.VISIBLE);
            }
        }


        ////SAVE POLL
        button_save?.setOnClickListener {

            if(questiontextview?.text.toString()==""){
                Toast.makeText(this, "Enter Question to Save", Toast.LENGTH_SHORT).show()
            }
            else if(option1?.text.toString()==""){
                Toast.makeText(this, "Enter Option1 to Save", Toast.LENGTH_SHORT).show()
            }
            else if(option2!!.isVisible){
                Toast.makeText(this, "You should atleast have 2 option's to Save", Toast.LENGTH_SHORT).show()

            }
            else if(option2?.text.toString()==""){
                Toast.makeText(this, "Enter Option2 to Save", Toast.LENGTH_SHORT).show()
            }
            else{
                question= questiontextview?.text.toString()
                optn1= option1?.text.toString()
                optn2= option2?.text.toString()
                optn3= option3?.text.toString()
                optn4= option4?.text.toString()
                optn5= option5?.text.toString()
                optn6= option6?.text.toString()
                optn7= option7?.text.toString()


                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            }

        }



        //////////////////Publish Poll BUTTON
        btnPublishPoll.setOnClickListener {

                var count=0;
                binding.chipsGroupAlltags.children
                    .toList()
                    .filter { (it as Chip).isChecked }
                    .forEach {

                        Log.i("chips", (it as Chip).text as String)
                        count++
                    }
                //Setting everything to deafult after uploading Poll
                for(i in 0..chipsArray.size-1){
                    chipsArray[i].isChecked=false
                }

//                for(i in 0.. optionsArray.size-1){
//                    optionsArray[i]?.setText("")
//                }


                numberOptions=1
//            for(i in 0..arrayVariableSave.size-1){
//                arrayVariableSave[i]=""
//
//            }
             question=""
            optn1=""
             optn2=""
             optn3=""
                 optn4=""
                 optn5=""
                optn6=""
                 optn7=""

                for(i in 1..optionsArray.size-1){

                    optionsArray[i]?.setVisibility(View.GONE);
                }



            }




    }


}