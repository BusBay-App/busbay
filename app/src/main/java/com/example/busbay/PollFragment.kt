package com.example.busbay

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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


    lateinit var readProgressLayout: RelativeLayout
    lateinit var readProgressBar : ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter:PollRVAdapter
    lateinit var proffession_year:String





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentPollBinding.inflate(inflater,container,false)
        //Firebase auth
        auth= Firebase.auth//different UI
        addPoll=binding.floatingActionButtonPoll



        recyclerView=binding.recyclerView
        readProgressLayout=binding.readProgressLayout
        readProgressBar=binding.readProgressBar
        layoutManager= LinearLayoutManager(requireActivity())

        proffession_year=getDefaults("Branch")+((getDefaults("PassOutYear")?.toInt())!! -2000-4).toString()






        addPoll.setOnClickListener { GotoAddPollactivity()}






        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val bookList= arrayListOf<BookofPoll>()
        val queue= Volley.newRequestQueue(requireActivity())
        val url="https://script.google.com/macros/s/AKfycbyWva0kwG5EbOYbLb1OV2G92Xn7sBTEodw6oPo5bqV5Z77dYm1QDIgMN4rqI8rY34zOZA/exec"

        val jsonObjectRequest=object : JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener {
                val data=it.getJSONArray("items")
                for(i in 0 until data.length()) {
                    val bookJasonObject = data.getJSONObject(i)
                    val listfAlltags =
                        bookJasonObject.getString("itembranchyear").split(" ").toTypedArray()

                    for (i in 0 until listfAlltags.size) {
                        if(listfAlltags[i]==""){
                            break
                        }
                        if(proffession_year!=listfAlltags[i]){
                            continue
                        }

                        val bookObject = BookofPoll(
                            bookJasonObject.getString("date").split(" ")[0],
                            bookJasonObject.getString("itememail_id"),
//                        bookJasonObject.getString("itembranchyear"),
                            listfAlltags[i],
                            bookJasonObject.getString("itemquestion"),
                            bookJasonObject.getString("itemoptn1"),
                            bookJasonObject.getString("itemoptn2"),
                            bookJasonObject.getString("itemoptn3"),
                            bookJasonObject.getString("itemoptn4"),
                            bookJasonObject.getString("itemoptn5"),
                            bookJasonObject.getString("itemoptn6"),
                            bookJasonObject.getString("itemoptn7"),
                            bookJasonObject.getString("itemc1"),
                            bookJasonObject.getString("itemc2"),
                            bookJasonObject.getString("itemc3"),
                            bookJasonObject.getString("itemc4"),
                            bookJasonObject.getString("itemc5"),
                            bookJasonObject.getString("itemc6"),
                            bookJasonObject.getString("itemc7")
                        )
//                    if(bookJasonObject.getString("itemRating")=="2"){
                        bookList.add(bookObject)
//                    }
                    }
                }

                readProgressLayout.visibility= View.GONE
                readProgressBar.visibility= View.GONE
                recyclerAdapter= PollRVAdapter(requireActivity(),bookList)
                recyclerView.adapter=recyclerAdapter
                recyclerView.layoutManager=layoutManager
            }, Response.ErrorListener {  }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        queue.add(jsonObjectRequest)




    }



    private fun GotoAddPollactivity() {
        val intent= Intent(getActivity(),AddPollActivity::class.java) ///
        startActivity(intent)
    }

    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        return preferences.getString(key, null)
    }



}