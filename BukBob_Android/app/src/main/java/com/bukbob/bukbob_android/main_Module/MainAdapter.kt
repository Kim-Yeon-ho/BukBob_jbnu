package com.bukbob.bukbob_android.main_Module

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bukbob.bukbob_android.R
import com.bukbob.bukbob_android.mainList_Module.FoodListAdapter
import java.text.SimpleDateFormat

class MainAdapter(var foodList: ArrayList<String>): RecyclerView.Adapter<MainAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.food_list,parent,false)){
        val foodTitle : TextView = itemView.findViewById(R.id.FoodTitle)
        val foodDate : TextView = itemView.findViewById(R.id.FoodDate)
        val foodTime : TextView = itemView.findViewById(R.id.FoodTime)
        val foodList : RecyclerView = itemView.findViewById(R.id.FoodListView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = 3
    //조식,중식,석식 페이지 3개

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        setTitle(holder,position)
        setTime(holder)
        setList(holder)
    }

    fun setList(holder: PagerViewHolder){
        val layoutManager = LinearLayoutManager(holder.itemView.context,LinearLayoutManager.VERTICAL,false)

        holder.foodList.layoutManager = layoutManager
        holder.foodList.adapter = FoodListAdapter(arrayListOf("1","2","3"))
    }


    fun setTitle(holder : PagerViewHolder,position: Int){
        when(position){
            0 -> {
                holder.foodTitle.text = "조식"
                holder.foodTime.text = "07:00 ~ 08:30"
            }
            1 -> {
                holder.foodTitle.text = "중식"
                holder.foodTime.text = "11:00 ~ 14:00"
            }
            2 -> {
                holder.foodTitle.text = "석식"
                holder.foodTime.text = "17:30 ~ 19:00"
            }
        }
    }

    fun setTime(holder: PagerViewHolder){
        val currentTime : Long = System.currentTimeMillis()
        val nowTime = SimpleDateFormat("MM/dd E")
        holder.foodDate.text = nowTime.format(currentTime)
    }


}