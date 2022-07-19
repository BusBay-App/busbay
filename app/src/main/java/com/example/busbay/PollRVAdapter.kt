package com.example.busbay

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PollRVAdapter(val context: Context, val itemList:ArrayList<BookofPoll>)
    : RecyclerView.Adapter<PollRVAdapter.ReadViewHolder>()  {

    private lateinit var mListner: onItemClickListner
    interface onItemClickListner{
        fun onItemClick(position: String)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner=listner
    }

    class ReadViewHolder(view: View,itemClickListner: onItemClickListner):RecyclerView.ViewHolder(view){



        val date :TextView=view.findViewById(R.id.txtv_Date)
        val itememail_id :TextView=view.findViewById(R.id.txtv_uploader_emailid)
        val itembranchyear :TextView=view.findViewById(R.id.txtv_branch)
        val itemquestion :TextView=view.findViewById(R.id.txtv_relative_question)
        val itemoptn1 :Button=view.findViewById(R.id.opt1)
        val itemoptn2 :Button=view.findViewById(R.id.opt2)
        val itemoptn3 :Button=view.findViewById(R.id.opt3)
        val itemoptn4 :Button=view.findViewById(R.id.opt4)
        val itemoptn5 :Button=view.findViewById(R.id.opt5)
        val itemoptn6 :Button=view.findViewById(R.id.opt6)
        val itemoptn7 :Button=view.findViewById(R.id.opt7)
        val itemc1 :TextView=view.findViewById(R.id.txtv_c1)
        val itemc2 :TextView=view.findViewById(R.id.txtv_c2)
        val itemc3 :TextView=view.findViewById(R.id.txtv_c3)
        val itemc4 :TextView=view.findViewById(R.id.txtv_c4)
        val itemc5 :TextView=view.findViewById(R.id.txtv_c5)
        val itemc6 :TextView=view.findViewById(R.id.txtv_c6)
        val itemc7 :TextView=view.findViewById(R.id.txtv_c7)

        init {
//            view.setOnClickListener {
//                itemClickListner.onItemClick(adapterPosition.toString())
//
//            }
            itemoptn1.setOnClickListener {
                itemClickListner.onItemClick(itemoptn1.text.toString())
            }
            itemoptn2.setOnClickListener {
                itemClickListner.onItemClick(itemoptn2.text.toString())
            }
            itemoptn3.setOnClickListener {
                itemClickListner.onItemClick(itemoptn3.text.toString())
            }
            itemoptn4.setOnClickListener {
                itemClickListner.onItemClick(itemoptn4.text.toString())
            }
            itemoptn5.setOnClickListener {
                itemClickListner.onItemClick(itemoptn5.text.toString())
            }
            itemoptn6.setOnClickListener {
                itemClickListner.onItemClick(itemoptn6.text.toString())
            }
            itemoptn7.setOnClickListener {
                itemClickListner.onItemClick(itemoptn7.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.single_poll,parent,false)
        return ReadViewHolder(view,mListner)
    }

    override fun onBindViewHolder(holder: ReadViewHolder, position: Int) {
        val book=itemList[position]
//        if(book.bookRating.toString()=="2"){
        holder.date.text=book.date
        holder.date.text=book.date
        holder.itememail_id.text=book.itememail_id
        holder.itembranchyear.text=book.itembranchyear
        holder.itemquestion.text=book.itemquestion
        holder.itemoptn1.text=book.itemoptn1
        holder.itemoptn2.text=book.itemoptn2
        holder.itemoptn3.text=book.itemoptn3
        holder.itemoptn4.text=book.itemoptn4
        holder.itemoptn5.text=book.itemoptn5
        holder.itemoptn6.text=book.itemoptn6
        holder.itemoptn7.text=book.itemoptn7
        holder.itemc1.text=book.itemc1
        holder.itemc2.text=book.itemc2
        holder.itemc3.text=book.itemc3
        holder.itemc4.text=book.itemc4
        holder.itemc5.text=book.itemc5
        holder.itemc6.text=book.itemc6
        holder.itemc7.text=book.itemc7
        if(book.itemoptn3==""){
            holder.itemoptn3.visibility = View.GONE
        }

        if(book.itemoptn4==""){
            holder.itemoptn4.visibility = View.GONE
        }
        if(book.itemoptn5==""){
            holder.itemoptn5.visibility = View.GONE
        }
        if(book.itemoptn6==""){
            holder.itemoptn6.visibility = View.GONE
        }
        if(book.itemoptn7==""){
            holder.itemoptn7.visibility = View.GONE
        }
//        holder.itemoptn1.setOnClickListener {
//           Log.i("Clicked",holder.itemoptn1.text.toString())
//
//        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }



}