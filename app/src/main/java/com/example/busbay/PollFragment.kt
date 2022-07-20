package com.example.busbay

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.busbay.databinding.FragmentPollBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PollFragment : Fragment()  {
    private lateinit var binding: FragmentPollBinding
    private lateinit var auth: FirebaseAuth
    lateinit var addPoll: FloatingActionButton //


    lateinit var readProgressLayout: RelativeLayout
    lateinit var readProgressBar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter: PollRVAdapter
    lateinit var proffession_year: String



    private lateinit var alldate_time :LiveData<List<DateTimeEntity>>
    private lateinit var  repository: DateTimeRepository

    init {



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPollBinding.inflate(inflater, container, false)
        //Firebase auth
        auth = Firebase.auth//different UI
        addPoll = binding.floatingActionButtonPoll


        val dao= DateTimeDatabase.getDatabase(requireActivity()).getDateTimeDao()
        repository=DateTimeRepository(dao)
        alldate_time=repository.alldatetime




        recyclerView = binding.recyclerView
        readProgressLayout = binding.readProgressLayout
        readProgressBar = binding.readProgressBar
        layoutManager = LinearLayoutManager(requireActivity())

        proffession_year =
            getDefaults("Branch") + ((getDefaults("PassOutYear")?.toInt())!! - 2000 - 4).toString()






        addPoll.setOnClickListener { GotoAddPollactivity() }






        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val bookList = arrayListOf<BookofPoll>()
        val queue = Volley.newRequestQueue(requireActivity())
        val url =
            "https://script.google.com/macros/s/AKfycbyWva0kwG5EbOYbLb1OV2G92Xn7sBTEodw6oPo5bqV5Z77dYm1QDIgMN4rqI8rY34zOZA/exec"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                val data = it.getJSONArray("items")
                for (i in 0 until data.length()) {
                    val bookJasonObject = data.getJSONObject(i)
                    val listfAlltags =
                        bookJasonObject.getString("itembranchyear").split(" ").toTypedArray()

                    for (i in 0 until listfAlltags.size) {
                        if (listfAlltags[i] == "") {
                            break
                        }
                        if (proffession_year != listfAlltags[i]) {
                            continue
                        }

                        val bookObject = BookofPoll(
                            bookJasonObject.getString("date"),
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

                readProgressLayout.visibility = View.GONE
                readProgressBar.visibility = View.GONE
                recyclerAdapter = PollRVAdapter(requireActivity(), bookList)

                //////
                recyclerAdapter.setOnItemClickListner(object : PollRVAdapter.onItemClickListner {
//                    override fun onItemClick(position: Int) {
//
//                        Toast.makeText(requireActivity(), "Clicked on $position", Toast.LENGTH_SHORT).show()
//                    }

                    override fun onItemClick(datee: String,optionn:String) {
//                        Toast.makeText(requireActivity(),"Clicked on $datee $optionn",Toast.LENGTH_SHORT).show()
                        ///Inserting in ROOOM
                        searchDatabase(datee).observe(requireActivity()) {
                            if(it.size.toString()=="0"){
                                updateItemToSheet(datee,optionn)
                                insertNode(DateTimeEntity(datee))
//                                Toast.makeText(requireActivity(), "this "+ it.size.toString(), Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(requireActivity(), "Already Voted", Toast.LENGTH_SHORT).show()
                            }
                        }

//                        searchDatabase(datee).observe(requireActivity()) {
////                            if(it.size==1){
//                            Toast.makeText(requireActivity(), "this2 "+ it.size.toString(), Toast.LENGTH_SHORT).show()
////                            }
//                        }
//                        upss()
                        iniRefreshListener()
                    }

                })
                ///
                recyclerView.adapter = recyclerAdapter
                recyclerView.layoutManager = layoutManager
            }, Response.ErrorListener { }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        queue.add(jsonObjectRequest)


    }
/////////////////////////////////////
    private fun updateItemToSheet(datee: String, optionn: String) {

        readProgressLayout.visibility = View.VISIBLE
        readProgressBar.visibility = View.VISIBLE
        val url="https://script.google.com/macros/s/AKfycbxd4dMKEl-eBGsnGKTYazF7RMhDYN9_Na-8bmxbMqFmntH9NQfNsUu_pvMhqjfR1lAklQ/exec"
//        val loading = ProgressDialog.show(requireActivity(), "Updating Item", "Please wait")
//        val name: String = editTextItemName.getText().toString().trim { it <= ' ' }
//        val brand: String = editTextBrand.getText().toString().trim { it <= ' ' }
//        val price: String = editTextPrice.getText().toString().trim { it <= ' ' }


        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
//                loading.dismiss()
                readProgressLayout.visibility = View.GONE
                readProgressBar.visibility = View.GONE
                Toast.makeText(requireActivity(), response, Toast.LENGTH_LONG).show()
//                when(optionn){
//                    "1"-> {}
//                    "2"-> {}
//                    "3"-> {}
//                    "4"-> {}
//                    "5"-> {}
//                    "6"-> {}
//                    "7"-> {}
//
//                }
//                val intent = Intent(requireActivity(), MainActivity::class.java)
//                startActivity(intent)
            },
            Response.ErrorListener {
                Toast.makeText(requireActivity(), "Error "+it.toString(), Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String>? {
                val parmas: MutableMap<String, String> = HashMap()


                //parmas
                parmas["Date"]=datee
//                parmas["Email_Id"]="email"
//                parmas["Branch_Year"]="CSE18 MnC18 EE18 ME18 CSE19 MnC19 EE19 ME19 CSE20 MnC20 EE20 ME20 CSE21 Mnc21 EE21 ME21 CSE22 MnC22 EE22 ME22 "
//                parmas["Question"]="question"
//                parmas["Option1"]="optn1"
//                parmas["Option2"]="optn2"
//                parmas["Option3"]="optn3"
//                parmas["Option4"]="optn4"
//                parmas["Option5"]="optn5"
//                parmas["Option6"]="optn6"
//                parmas["Option7"]="optn7"

//                parmas["c1"]="1"
//                parmas["c2"]="1"
//                parmas["c3"]="1"
//                parmas["c4"]="1"
//                parmas["c5"]="1"
//                parmas["c6"]="1"
//                parmas["c7"]="1"
                parmas["option_no_selected"]=optionn

                return parmas
            }
        }

//        val socketTimeOut = 50000 // u can change this .. here it is 50 seconds


//        val retryPolicy: RetryPolicy =
//            DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
//        stringRequest.retryPolicy = retryPolicy

        val queue = Volley.newRequestQueue(requireActivity())

        queue.add(stringRequest)


    }
///////////////////////////////////

    private fun GotoAddPollactivity() {
        val intent = Intent(getActivity(), AddPollActivity::class.java) ///
        startActivity(intent)
    }

    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        return preferences.getString(key, null)
    }
    fun insertNode(note : DateTimeEntity) = GlobalScope.launch(Dispatchers.IO){
        repository.insert(note)
    }
    fun searchDatabase(searchQuery: String) : LiveData<List<DateTimeEntity>>{
        return repository.search_date_time(searchQuery)
    }

    //refresh
    fun iniRefreshListener() {
        val swipeRefreshLayout=binding.swipeLayout
        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { // This method gets called when user pull for refresh,
            // You can make your API call here,
            val handler = Handler()
            handler.postDelayed(Runnable {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false)
                }
            }, 3000)
        })
        onResume()
    }


}