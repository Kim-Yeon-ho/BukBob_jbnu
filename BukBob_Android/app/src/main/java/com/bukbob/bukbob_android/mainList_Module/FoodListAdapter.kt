package com.bukbob.bukbob_android.mainList_Module

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bukbob.bukbob_android.R

class FoodListAdapter (val FoodList: ArrayList<String>): RecyclerView.Adapter<FoodListAdapter.ViewHolder>(){

    var isWidget : Boolean = false

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var foodMarket : TextView
        var foodData : TextView
        var widget : ImageButton

        init {
            foodMarket = view.findViewById(R.id.food_market)
            foodData = view.findViewById(R.id.FoodList)
            widget = view.findViewById(R.id.widgetButton)
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
        holder.foodData.text = "맛있는 밥 \n커리 돈까스 \n겉절이 김치 \n요플레"
        //차후 수정
        holder.widget.setOnClickListener {

            if(isWidget){
                isWidget = !isWidget
                holder.widget.setImageResource(R.drawable.starnonfill)
            }else{
                isWidget = !isWidget
                holder.widget.setImageResource(R.drawable.starfill)
            }

        }
    }
}