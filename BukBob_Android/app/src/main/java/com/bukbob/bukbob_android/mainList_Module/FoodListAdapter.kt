package com.bukbob.bukbob_android.mainList_Module

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bukbob.bukbob_android.MainActivity
import com.bukbob.bukbob_android.R
import com.bukbob.bukbob_android.main_Module.MainViewModel

class FoodListAdapter (val FoodList: ArrayList<String>,owner : MainActivity): RecyclerView.Adapter<FoodListAdapter.ViewHolder>(){

    val owner = owner
    var isTrue = false
    inner class ViewHolder(parent: View) : RecyclerView.ViewHolder(parent){
        var foodMarket : TextView
        var foodData : TextView
        var widget : ImageButton

        init {
            foodMarket = parent.findViewById(R.id.food_market)
            foodData = parent.findViewById(R.id.FoodList)
            widget = parent.findViewById(R.id.widgetButton)
        }
    }

    /**
     * 해당 어댑터는 food_list_item_view를 기반으로 작성됐습니다.
     *
     * foodMarket는 food_list_item_view안에 식당 타이틀을 관리합니다.
     * foodData는 food_list_item_view안에 식단 리스트를 관리합니다.
     * widget는 food_list_item_view안에 widget 리스트를 관리합니다.
     *
     * */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.food_list_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 4
    //차후 수정

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.foodMarket.text = "진수당"
        holder.foodData.text = "맛있는 밥 \n커리 돈까스 \n겉절이 김치 \n요플레"


        val model : MainViewModel = ViewModelProvider(owner).get(MainViewModel::class.java)

        if(model.position.value == position && model.isCheck.value == true){
            holder.widget.setImageResource(R.drawable.starfill)
        }else{
            holder.widget.setImageResource(R.drawable.starnonfill)
        }


        holder.widget.setOnClickListener {
            model.setPosition(position)

            if(model.isCheck.value == true){
                isTrue = false
                holder.widget.setImageResource(R.drawable.starfill)
                model.setIsCheck(false)
            }else{
                isTrue = true
                holder.widget.setImageResource(R.drawable.starnonfill)
                model.setIsCheck(true)
            }
        }



    }

}