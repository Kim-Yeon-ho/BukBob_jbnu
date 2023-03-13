package com.bukbob.bukbob_android.mainList_Module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bukbob.bukbob_android.R

class FoodListAdapter (val FoodList: ArrayList<String>): RecyclerView.Adapter<FoodListAdapter.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var foodMarket : TextView
        var data : TextView

        init {
            foodMarket = view.findViewById(R.id.food_market)
            data = view.findViewById(R.id.FoodList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_list_item,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 4
    //차후 수정

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.foodMarket.text = "진수당"
        holder.data.text = "맛있는 밥 \n커리 돈까스 \n겉절이 김치 \n요플레"
        //차후 수정
    }
}